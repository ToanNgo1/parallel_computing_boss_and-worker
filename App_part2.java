package project3.part2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;


public class App_part2 
{
	public static void main(String[] args) {

    	// The ActorSystem extends “ActorRefFactory“ which is a hierarchical group of actors which share common configuration. 
    	// It is also the entry point for creating or looking up actors.
    	ActorSystem system = ActorSystem.create("Hello");

    	
        //ActorRef is an immutable and serializable handle (thread-safe and fully share-able) to an actor.
    	//the first input is the class you use to create that actor
    	//the second input is the optional name you give to that actor
    	ActorRef basicActor = system.actorOf(Props.create(Boss_Actor_part2.class), "BossActor");
        
        //system.stop(a);
        //system.awaitTermination();
        //system.shutdown();
    }
}
