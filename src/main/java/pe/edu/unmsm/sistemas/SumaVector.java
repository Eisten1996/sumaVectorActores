package pe.edu.unmsm.sistemas;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

import javax.swing.text.Segment;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class SumaVector extends UntypedAbstractActor {

    private int vec[];
    private long total;
    private int segment;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        // inicializamos el vector
        inicializar();
        segment = 4;
        total = 0;

        Sumar sumar[] = new Sumar[4];
        ActorRef sumador[] = new ActorRef[4];

        for (int i = 0; i < 4; i++) {

            sumar[i] = new Sumar();
            sumar[i].vec = vec;
            sumar[i].ini = (int) i * vec.length / 4;
            sumar[i].fin = (int) (i + 1) * vec.length / 4;

            sumador[i] = this.getContext().actorOf(Props.create(Sumador.class), "Sumador" + i);
            sumador[i].tell(sumar[i], this.getSelf());
        }

    }

    public void onReceive(Object message) throws Throwable {
        if (message instanceof Long) {
            total += (Long) message;
//			System.out.println("la suma parcial es " + (Long) message);
            segment--;
            if (segment == 0) {
                System.out.println("la suma del vector es " + total);
                this.getContext().stop(getSelf());
            }
        }
    }

    private void inicializar() {


        vec = new int[12];
        for (int i = 0; i < vec.length; i++) {
            vec[i] = i;
        }

        /*
         * Random ale = new Random(10000); vec = new int[20000]; for (int i = 0; i <
         * vec.length; i++) { vec[i] = Math.abs(ale.nextInt(1024)); }
         */
    }
}
