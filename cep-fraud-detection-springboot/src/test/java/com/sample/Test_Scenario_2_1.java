package com.sample;

import static com.sample.Utils.form;
import static com.sample.Utils.newSessionFromClasspathContainer;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import com.sample.model.Transaction;
import com.sample.model.TransactionType;

public class Test_Scenario_2_1 {

public static void main(String[] args) {
        
        KieSession ksession = newSessionFromClasspathContainer("ksession-cep-fraud-detection");

        for(int i = 0 ; i < 10; i ++) {
          fireRules(ksession, form("USE1001" + i, 10 + i, TransactionType.CREDIT));
        }
        
        EntryPoint e = ksession.getEntryPoint("Credit Card");
        System.out.println(e.getFactCount());
        
        ksession.dispose();
        
    }

    private static void fireRules(KieSession ksession, Transaction transaction) {

        EntryPoint entryPoint = ksession.getEntryPoint("Credit Card");
        if (entryPoint != null) {
            entryPoint.insert(transaction);
        }
        ksession.insert(transaction);
        ksession.fireAllRules();
//        if(transaction.isDenied()) {
//            System.out.println(transaction + " Denied: " + transaction.getDeniedCause());
//        } else {
//            System.out.println(transaction + " Accept");
//        }
    }
}
