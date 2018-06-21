/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.solutec;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author esic
 */
public class IdentifiantsBD {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("solutec-lyon_SwingIdentifiants_jar_1.0-SNAPSHOTPU");
     EntityManager em = emf.createEntityManager();
     
    public boolean ajouterIdentifiant(String login, String mdp){
    boolean test=true;
    ArrayList<Identifiants> listeIdentifiants = new ArrayList<>();
    listeIdentifiants.addAll(getListIdentifiants());
   
    
        for (int i = 0; i < listeIdentifiants.size() ; i++) {
            
       if (listeIdentifiants.get(i).getLogin().equals(login)) {
           test=false;       
           break;
                }
        }
        
        if (test) { 
            
                    Identifiants identifiants = new Identifiants();
                    identifiants.setLogin(login);
                    identifiants.setMdp(mdp);
                    this.persist(identifiants);    
        }
    
   return test;
    }
    
    public Collection getListIdentifiants() {        //Collection (Haut) --> List --> ArrayList (Bas)

    Query q = em.createQuery("SELECT u from Identifiants u");    //Utilisateur représente ici la classe Entity, et non la table UTILISATEUR ! (bonne orthographe de la classe)
    return q.getResultList();          //pas de pb avec List, car List fait partie de Collection, et Collection est placé plus haut que List 

    }
    
    public boolean supprimerIdentifiant(String login){
        /*
    String req = "DELETE FROM Identifiants u WHERE u.login = :ident";
    Query q = em.createQuery(req);
    q.setParameter("ident", login);
    q.executeUpdate();
    */
    boolean test=false;
    ArrayList<Identifiants> listeIdentifiants = new ArrayList<>();
    listeIdentifiants.addAll(getListIdentifiants());
   
    
        for (int i = 0; i < listeIdentifiants.size() ; i++) {
            
       if (listeIdentifiants.get(i).getLogin().equals(login)) {
           test=true;       
           break;
                }
        }   
        
     if (test){ 
     String req = "SELECT u from Identifiants u WHERE u.login= :idLogin";  
     Query q = em.createQuery(req);
     q.setParameter("idLogin", login);
     Identifiants e = (Identifiants) q.getSingleResult();  
     //System.out.println(e.getId());
     
     em.getTransaction().begin();
     
     String req2 = "DELETE FROM Identifiants i WHERE i.id= :idEmp";
     Query q2 = em.createQuery(req2);
     q2.setParameter("idEmp", e.getId());
     q2.executeUpdate();
     
     em.getTransaction().commit();
     
     }
   return test;
    }

    public boolean modifierIdentifiant(String login, String nouvLogin, String nouvMdp){
     boolean test =true;
      ArrayList<Identifiants> listeIdentifiants = new ArrayList<>();
      listeIdentifiants.addAll(getListIdentifiants());
   
        for (int i = 0; i < listeIdentifiants.size() ; i++) {
            
       if (listeIdentifiants.get(i).getLogin().equals(nouvLogin) && !nouvLogin.equals(login) ) {
           test=false;       
           break;
                }
        }
        
    if (test){ 
    String req = "SELECT u from Identifiants u WHERE u.login= :idEmp";  
    Query q = em.createQuery(req);
    q.setParameter("idEmp", login);
    Identifiants e = (Identifiants) q.getSingleResult();
    e.setLogin(nouvLogin);
    e.setMdp(nouvMdp);
    //Identifiants ident = (Identifiants) em.find(Identifiants.class, 1);
    //ident.setLogin(nouvLogin);
    //ident.setMdp(nouvMdp);
    this.persist(e);
    }
    
    return test;
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
