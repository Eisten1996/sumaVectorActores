package pe.edu.unmsm.sistemas;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class Main {
    int vec[] = new int[12];

    public static void main(String[] args) throws IOException {
        /*
        String[] actores = new String[1];
        actores[0] = SumaVector.class.getName();
        akka.Main.main( actores );
        */
        Main main1 = new Main();
        main1.inicializar();

        ActorSystem system = ActorSystem.create("suma-vector");
        ActorRef sumavector1 = system.actorOf(Props.create(SumaVector1.class));
        sumavector1.tell( main1.vec, ActorRef.noSender() );
        system.terminate();
    }

    private void inicializar() throws IOException {
        // inicializo el vector
        InputStream is = getClass().getClassLoader().getResourceAsStream("data.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bis = new BufferedReader( isr );
        ArrayList<Integer> lista = new ArrayList<Integer>();
        String linea = bis.readLine();
        while ( linea != null ) {
            lista.add(Integer.parseInt(linea));
            linea = bis.readLine();
        }

        // System.err.println("Tamaño " + lista.size() );
        vec = new int[lista.size()];
        for ( int i=0; i<lista.size(); i++ ) {
            vec[i] = lista.get(i);
        }

        /*
        vec = new int[12];
        for( int i=0; i<vec.length; i++ ) {
            vec[i] = i;
        }
        */

        /*
        Random ale = new Random(10000);
        for (int i = 0; i < vec.length; i++) {
            vec[i] = Math.abs(ale.nextInt(1024));
        }
        */
    }

}
