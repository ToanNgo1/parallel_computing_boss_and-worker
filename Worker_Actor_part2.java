package project3.part2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Worker_Actor_part2 extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ArrayList <Integer> outpacketEX=new ArrayList<Integer>();
    private  Random random_number = new Random();

    
    @Override
    public void preStart() {
        log.info("Starting");
        populate(outpacketEX);
    }

    
    @Override
    public void onReceive(Object msg) {
        //what to do when message is received
        //ArrayList<String> test=new ArrayList<>();
        if (msg instanceof ArrayList)//int [] this is a int array object, arraylist
        {
            log.info("receive object");
            //Object[] objects = (msg);
            //for (Object obj : objects)
            //System.out.println(obj);
            //System.out.println(msg[0]);
           /*   for(Integer i: (ArrayList<Integer>)msg)
            {
                System.out.println(i+" ...check this");
            }*/
            //Integer[] conver=(ArrayList<Integer>)msg.toArray();
            String[] pack=output((ArrayList<Integer>)msg);
            //String[] sample=new String[]{"this","that is"};
            
            //ArrayList<String> container = new ArrayList<>(Arrays.asList("sub0", "subs1"));
            //Object[] o=container.toArray();
            getSender().tell(pack, getSelf());
            getSender().tell("done with the work", getSelf()); 

        }
    	
        if (msg instanceof String && msg.equals("hello worker")) {
        	
        	log.info("Recevied GreetingMessage!");
            
            getSender().tell("done with the work", getSelf());    //respond to the sender
            
        }
        else if(msg instanceof String && msg.equals("Task: Report Statistics"))
        {
            log.info("received message report");
            //populate(outpacketEX);
            String[] extract=output(outpacketEX);
            getSender().tell(extract,getSelf());
            getSender().tell("done with the work", getSelf());
            

        }
         else if (msg instanceof String && msg.equals("terminate")) {
        	
        	log.info("Recevied terminate message!");
            
        	 getContext().stop(getSelf());                               //stop the sender and with it the application
            
        } else if (msg instanceof String && msg.equals("identified terminate")) {
        	
        	log.info("Recevied identified terminate message!");
            
        	 getContext().stop(getSelf());                               //stop the sender and with it the application
            
        } else {
            unhandled(msg);
        }
    }
    public void populate(ArrayList<Integer> outpacketEX)//populate the local array worker. 
    {
        for (int i=0 ; i<2000;i++)
        {   
            outpacketEX.add(random_number.nextInt(50));
        }
    }
    public static String[] output(ArrayList<Integer> input)//this will get call either using a passby array or a local array
    {
        Integer counter=0;
        Integer advegave=0;
        Integer sumation=0;
        Integer max=-999;
        String[] outpacket=new String[3];
        //working for sum//
        for(Integer i: input)
        {
            sumation+=i;
            counter+=1;
            if(i >max)
            {
                max=i;
            }
        }
        //find advegave//
            advegave=sumation/counter;
            //System.out.println(counter+"il");
           //System.out.println(sumation);
        //conver to string
        for(int a=0;a<outpacket.length;a++)
        {
            if(a==0)
            {
            outpacket[a]="The summation is: "+String.valueOf(sumation);
            }
            else if(a==1)
            {
                outpacket[a]="The average is: "+String.valueOf(advegave);
            }
            else
            {
                outpacket[a]="The max value is: "+String.valueOf(max);
            }
        }
        return outpacket;
    }
    
    
    @Override
    public void postStop() {         
     	log.info("terminating");
    }
}