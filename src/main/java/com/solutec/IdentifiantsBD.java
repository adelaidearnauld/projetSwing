/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutec;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author esic
 */
public class IdentifiantsBD {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("solutec-lyon_SwingIdentifiants_jar_1.0-SNAPSHOTPU");
     EntityManager em = emf.createEntityManager();
     
    public void ajouterIdentifiant(String login, String mdp){
    
    Identifiants identifiants = new Identifiants();
    identifiants.setLogin(login);
    identifiants.setMdp(mdp);
    this.persist(identifiants);
    }

    public void persist(Object object) {
       
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            //em.close();
        }
    }
    
}
