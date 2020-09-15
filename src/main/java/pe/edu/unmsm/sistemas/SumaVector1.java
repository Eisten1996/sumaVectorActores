package pe.edu.unmsm.sistemas;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class SumaVector1 extends UntypedAbstractActor {

    private int vec[];
    private long total;
    private int segme;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        // inicializar();
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Long) {
            total += (Long) message;
            segme--;
            if (segme == 0) {
                System.out.println("La suma total del vector es " + total);
                this.getContext().stop(getSelf());
            }
        } else if (message instanceof int[]) {
            total = 0;
            segme = 4;
            vec = (int[]) message;
            Sumar sumar[] = new Sumar[4];
            ActorRef sumador[] = new ActorRef[4];

            for (int i = 0; i < 4; i++) {
                sumar[i] = new Sumar();
                sumar[i].vec = vec;
                sumar[i].ini = (int) (i / 4.0 * sumar[i].vec.length);
                sumar[i].fin = (int) ((i + 1) / 4.0 * sumar[i].vec.length);
                sumador[i] = this.getContext().actorOf(
                        Props.create(Sumador.class),
                        "Sumador" + i
                );
                sumador[i].tell(
                        sumar[i],
                        this.getSelf()
                );
            }
        }
    }

}
