package bicycle.co;

import bicycle.co.DAO.VersionDao;
import bicycle.co.model.Annotation;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;

public class AnnotationParser {
    private final VersionDao dao;
    private final BufferedReader reader;
    private static String jarPath;

    public static void main(String[] args) throws Exception {
        jarPath = args[0];
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("annotation-unit")
                .createEntityManager();
        VersionDao dao = new VersionDao(entityManager);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        new AnnotationParser(dao, reader).run();
        entityManager.getEntityManagerFactory().close();
    }

    public AnnotationParser(VersionDao dao, BufferedReader reader) {
        this.dao = dao;
        this.reader = reader;
    }

    private void run() throws IOException {
        while (true) {
            System.out.println("Выберите опцию: "
                    + "1) Считать аннотации из jar-файла и записать в базу "
                    + "2) Просмотреть все записи из БД "
                    + "0) Выход");
            int option = Integer.parseInt(reader.readLine());

            switch (option) {
                case 1:
                    scanForAnnotationsAndSave();
                    break;
                case 2:
                    fetchAll();
                    break;
                case 0:
                    return;
            }
        }
    }

    public void scanForAnnotationsAndSave() {
        String pathToJar = "C:\\project2\\annparser\\src\\main\\resources\\myapp-1.0-SNAPSHOT.jar";

        URL[] urls = new URL[0];
        try {
            urls = new URL[]{new URL("jar:file:" + jarPath + "!/")};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLClassLoader urlcl = new URLClassLoader(urls);
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(
                        ClasspathHelper.forClassLoader(urlcl)
                ).addClassLoader(urlcl).setScanners(new MethodAnnotationsScanner(),
                        new TypeAnnotationsScanner(), new SubTypesScanner())
        );

        Set<Method> annoMethods = reflections.getMethodsAnnotatedWith(Version.class);
        for (Method m : annoMethods) {
            Annotation an = new Annotation(m.getAnnotation(Version.class).date(),
                    m.getName(),
                    "method",
                    m.getAnnotation(Version.class).author(),
                    m.getAnnotation(Version.class).comment());
            dao.persist(an);
        }

        Set<Class<?>> annoClasses = reflections.getTypesAnnotatedWith(Version.class);
        for (Class c : annoClasses) {
            Version v = (Version) c.getAnnotation(Version.class);
            Annotation an = new Annotation(v.date(),
                    c.getName(),
                    "class",
                    v.author(),
                    v.comment());
            dao.persist(an);
        }
    }

    public void fetchAll() {
        List<Annotation> list = dao.findAll();
        System.out.println("Записи:");
        list.forEach(e -> System.out.println(String.format("%s %s: %s - %s - %s", e.getType(), e.getName(), e.getDate(),
                e.getAuthor(), e.getComment())));
    }
}
