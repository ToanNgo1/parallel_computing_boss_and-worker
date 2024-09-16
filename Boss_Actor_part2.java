package project3.part2;

//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.*;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Boss_Actor_part2 extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ArrayList<Integer> main  =new ArrayList<>();
    private ArrayList<Integer> subs0=new ArrayList<>();
    private ArrayList<Integer> subs1=new ArrayList<>();
    private ArrayList<Integer> subs2=new ArrayList<>();
    private ArrayList<Integer> subs3=new ArrayList<>();
    private Object[] intArr = { subs0,subs1,subs2,subs3 }; 
    //private ArrayList<String> container = new ArrayList<>(Arrays.asList("sub0", "subs1"));
    private  Random random_number = new Random();

    @Override
    public void preStart() {

        log.info("Starting");
        populate();
       /* for(Object i: intArr)
        {
            System.out.println(Object.toString(i)+"this is the out");
        }
        
        /*for(String i: container)
        {System.out.println(i);
           if(container instanceof ArrayList)
           {System.out.println("this is a test");}
        }*/
        
        ActorRef workers[] = new ActorRef [4];             //create an array of workers
        
        for (int i = 0; i < 2; i++) {
        	log.info("creating worker #" +  i);
            
        	String name="Worker"+i;                       //the name should be a string
        	
            workers[i] = getContext().actorOf(Props.create(Worker_Actor_part2.class), name);

           // workers[i].tell(new String("hello worker"), getSelf());
           workers[i].tell(new String("Task: Report Statistics"), getSelf());
          /*  if(i==0)
            {workers[i].tell(intArr[i], getSelf());}
            else if(i==1)
            {
                workers[i].tell(subs1, getSelf());
            }
            else if(i==2)
            {
                workers[i].tell(subs2, getSelf());
            }
            */
           // workers[i].tell(intArr[i], getSelf());//done 

        }
    }

    public void populate()
    {
        //this populate the array
        //int count=0;
        for (int i=0 ;i< 8000;i++)
        {   
            main.add(random_number.nextInt(100));
            //count+=1;
        }
        //System.out.println(main.get(count-1)+" this is the out");
        //System.out.println(count-1 +": this is the count");
        int current_po=0;
        //int track=0;
        while(current_po< 8000)
        { //int remain=main.length-current_po+1;
            
            if(current_po<2000)
            {
                subs0.add(main.get(current_po));
            }
            else if(current_po>=2000 && current_po<4000)
            {
                /*if(current_po==200)
                {
                    track=0;
                }*/
                subs1.add(main.get(current_po));

            }
            else if(current_po>=4000 && current_po<6000)
            {
                /*if(current_po==4000)
                {
                    track=0;
                }*/
                subs2.add(main.get(current_po));
            }
            else if(current_po>=6000 && current_po<8000)
            {
                /*if(current_po==6000)
                {
                    track=0;
                }*/
                subs3.add(main.get(current_po));

            }
            current_po+=1;

        } 
        /*for(int i:subs0)
        {

        }
        for(int i:subs1)
        {

        }*/

    }
    @Override
    public void onReceive(Object msg) {
        if (msg instanceof String[])
        {
            //log.info("the sender is "+getSender().path().name().toString());
            //Objects[] arr=msg;
            //System.out.println(msg.toString()+"check this out");
            //System.out.println(msg.size);
            String ouputex="";
            for(String i:( String[])msg)
            {
                //System.out.println(i+": this bad");
                ouputex+=i+", ";
            }
            log.info("the sender is "+getSender().path().name().toString()+"\nrecieve message: "+ouputex);
        }
        
        if (msg instanceof String && msg == "done with the work") {

        	
        	log.info("the sender of this message is " + getSender().path().name().toString());  //print the identity of the sender, the name of the sender
        	log.info("recieve message: "+ msg.toString());
        	
        	if(getSender().path().name().toString().equals("Worker0")) {
        		
        		ActorRef W0 = (ActorRef)getContext().getChild("Worker0");            //get a reference for Worker0
                
                W0.tell("identified terminate", getSelf());                          //use that reference to send dedicated message
                
        	} else{
        		getSender().tell("terminate", getSelf());                            //whoever was the sender, send the terminate message
        	}
            
    	}else {
            unhandled(msg);
    	}

        
    }
    
    
    @Override
    public void postStop() {         
    	//what to do when terminated
     	log.info("terminating the Boss actor");
    }
}