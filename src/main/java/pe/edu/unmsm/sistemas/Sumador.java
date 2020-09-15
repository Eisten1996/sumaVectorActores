package pe.edu.unmsm.sistemas;

import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;

public class Sumador extends UntypedAbstractActor {

//    Logger logger = LoggerFactory.getLogger(Sumador.class);

	class SUMA {
		int ini;
		int fin;
		int vec[];
	}

	public void onReceive(Object message) throws Throwable {
		if (message instanceof Sumar) {
			Sumar suma = (Sumar) message;
			long total = realizarSuma(suma);
			ActorRef jefe = this.getSender();
			jefe.tell(new Long(total), this.getSelf());
		}

	}

	private long realizarSuma(Sumar sumar) {
		long total = 0;
		for (int i = sumar.ini; i < sumar.fin; i++) {
			total += sumar.vec[i];
		}
		System.out.println("la suma parcial es " + total);

		return total;
	}
}
