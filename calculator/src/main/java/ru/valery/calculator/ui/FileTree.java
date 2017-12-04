package ru.valery.calculator.ui;

import ru.valery.pluginloader.PluginLoader;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


public abstract class FileTree extends JPanel {
    private Timer timer;
    private int hashJar = 0;
    private JScrollPane currentPane;

    public FileTree(File dir, PluginLoader pluginLoader) {
        this.timer = new Timer();


        setLayout(new BorderLayout());

        //Обновлять список плагинов будем по таймеру
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int hash = getDirHash();

                if (hash== hashJar)
                    return;

                hashJar = hash;

                //Строим дерево от папки
                DefaultMutableTreeNode nodes = addNodes(null, dir);

                JTree tree = new JTree(nodes);

                //Подписываемся на выбор узла дерева - нужно для загрузки выбранного плагина
                tree.addTreeSelectionListener(nd -> {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) nd.getPath().getLastPathComponent();

                    File[] jars = dir.listFiles(file -> file.isFile() && file.getName().contains(node.toString()));

                    for (int i = 0; i < jars.length; i++) {
                        try {
                            System.out.println("Загружаем плагин " + node);

                            pluginLoader.putPlugin(jars[i]);
                            pluginUpdated();

                        } catch (MalformedURLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                });

                JScrollPane scrollpane = new JScrollPane();
                scrollpane.getViewport().add(tree);
                add(BorderLayout.CENTER, scrollpane);

                //Удаляем старую панель
                if(currentPane!=null){
                    FileTree.this.remove(currentPane);
                }
                currentPane = scrollpane;

                FileTree.this.validate();
                FileTree.this.repaint();
            }

            /**
             *
             * @return Хэш по списку файлов из корневой директории
             */
            private int getDirHash() {
                //Простая проверка каталога на наличие изменений, чтобы не перегружать дерево
                int hash = 0;
                for(File file : dir.listFiles(f->f.isFile()&&f.getName().endsWith(".jar"))){
                    hash+=file.getName().hashCode();
                }
                return hash;
            }
        }, 1000, 1000);

    }

    /**
     * Строим дерево каталогов/файлов
     * Метод не был написан с нуля - взят готовый скэтч
     * @param curTop
     * @param dir
     * @return
     */
    DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
        if (curTop != null) {
            curTop.add(curDir);
        }

        Vector ol = new Vector();
        String[] tmp = dir.list();
        for (int i = 0; i < tmp.length; i++)
            ol.addElement(tmp[i]);
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
        File f;
        Vector files = new Vector();

        for (int i = 0; i < ol.size(); i++) {
            String thisObject = (String) ol.elementAt(i);
            String newPath;
            if (curPath.equals("."))
                newPath = thisObject;
            else
                newPath = curPath + File.separator + thisObject;
            if ((f = new File(newPath)).isDirectory())
                addNodes(curDir, f);
            else
                files.addElement(thisObject);
        }

        for (int fnum = 0; fnum < files.size(); fnum++)
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        return curDir;
    }

    public Dimension getMinimumSize() {
        return new Dimension(200, 400);
    }

    public Dimension getPreferredSize() {
        return new Dimension(300, 400);
    }

    /**
     * Метод сигнализирующий о том, что был загружен новый плагин
     * Сигнал для отображения новой кнопки
     */
    public abstract void pluginUpdated();

}


