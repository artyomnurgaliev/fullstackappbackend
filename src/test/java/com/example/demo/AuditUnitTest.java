package com.example.demo;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.junit.Before;
import org.junit.Test;


public class AuditUnitTest {

    private SessionFactory sessionFactory;

    @Before
    public void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        try {
            MetadataSources metadataSources = new MetadataSources(registry);
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = new User("artyom", "", "", "", "");
        session.save(user);

        Project project = new Project("first", "",  "");
        project.setUserid(user);
        session.save(project);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testAuditing() {
        Session session = sessionFactory.openSession();
        AuditReader reader = AuditReaderFactory.get(session);

        AuditQuery query1 = reader.createQuery()
                .forEntitiesAtRevision(User.class, 2);

        AuditQuery query2 = reader.createQuery()
                .forRevisionsOfEntity(User.class, true, true);

        AuditQuery query3 = reader.createQuery()
                .forEntitiesAtRevision(Project.class, 2);

        AuditQuery query4 = reader.createQuery()
                .forRevisionsOfEntity(Project.class, true, true);

        System.out.println("Query 1: " + query1.getResultList().size());
        System.out.println("Query 1: " + query1.getResultList().get(0));

        System.out.println("Query 2: " + query2.getResultList().size());
        System.out.println("Query 2: " + query2.getResultList().get(0));

        System.out.println("Query 3: " + query3.getResultList().size());
        System.out.println("Query 3: " + query3.getResultList().get(0));

        System.out.println("Query 4: " + query4.getResultList().size());
        System.out.println("Query 4: " + query4.getResultList().get(0));
        session.close();
    }

}
