/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carpooling_package;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class CarPooling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        car c1=new car("1211",3, 2, "ghg", new route("giza", "abasia"));
        car c2=new car("9511",4, 2, "ghg", new route("alex", "abasia"));
        car c3=new car("5411",6, 2, "ghg", new route("cairo", "abasia"));
        car[] c= {c1,c2,c3};
//        nonSubscriber s= new nonSubscriber();
//        s.searchRoutes(c);
          Scanner input= new Scanner(System.in);
//        int i=input.nextInt();
//        s.reserveTicket(c, i);
//        s.subscribe(30,15);
//        s.reserveTicket(c, i);

        
        boolean con=true;
        while(con)
        {
            System.out.println("1. Subscriber\n2. non-subscriber");
            passenger p;
            passengerFactory fac=new passengerFactory();
            int i=input.nextInt();
        
            switch(i)
            {
                case 1:
                    System.out.println("Enter your number of trips : ");
                    int numoftrips=input.nextInt();
                    p=fac.getPassenger("subscriber",numoftrips);
                    System.out.println("1. unsubscribe\n2. Reserve a ticket");
                    int choice= input.nextInt();
                    switch(choice)
                    {
                        case 1:
                            subscriber s=(subscriber)p;
                            s.unSubscribe();
                            System.out.println("do you want to reserve a ticket? (y/n)");
                            String l=input.next();
                            if(l.equals("n"))
                                break;                            
                        case 2:
                            p.searchRoutes(c);
                            int carX=input.nextInt();
                            p.reserveTicket(c, carX);
                            break;                           
                    }
                    break;
                    
                case 2:
                    p=fac.getPassenger("non subscriber", 0);
                    System.out.println("1. subscribe\n2. Reserve a ticket");
                    int choice2= input.nextInt();
                    switch(choice2)
                    {
                        case 1:
                            nonSubscriber n=(nonSubscriber)p;
                            System.out.println("Enter your age : ");
                            int age = input.nextInt();
                            System.out.println("Enter number of tickets to be reserved");
                            int numoftickets=input.nextInt();
                            n.subscribe(age,numoftickets);
                            System.out.println("do you want to reserve a ticket?(y/n)");
                            String k=input.next();
                            if(k.equals("n"))
                                break;
                            
                        case 2:
                            p.searchRoutes(c);
                            int carX=input.nextInt();
                            p.reserveTicket(c, carX);
                            break;
                    }
                    break;
            }
            System.out.println("back to main menu? (y/n)");
            String x = input.next();
            if (x.equals("n"))
                con = false;
        }
        
    
    }
    
}

interface passenger
{
    default void searchRoutes(car[]c)
    {
        for(int i=0;i<c.length;i++)
        {
            System.out.println(i+1+". From "+ c[i].getRoute().getStartlocation()+" to "+ c[i].getRoute().getDestination());
        }
    }
    public abstract void reserveTicket(car[]c,int choice);
}
class subscriber implements passenger
{
    ticket ticket;
    private double discount=0.5;
    private int numOftrips;
    private boolean subscribed;

    public subscriber( int numOftrips) {
        this.ticket = new ticket();
        this.numOftrips = numOftrips;
        this.subscribed = true;
    }
 
    public void unSubscribe()
    {
        numOftrips=0;
        subscribed=false;
        System.out.println("unsubscribed successfully");
    }

    @Override
    public void reserveTicket(car[]c,int choice) {
        int carIndex=choice-1;
        if(subscribed==true)
        {
            ticket.setCarCode( c[carIndex].getCode());
            ticket.setPrice(discount*100);
            numOftrips--;
            if (numOftrips<0)
            {
                try{
                    numOftrips=0;
                    throw new numOfTrips("you don't have avilable trips");
                }catch(numOfTrips n){
                    System.out.println(n.getMessage());
                }
            }
            else
                System.out.println("ticket reserved, yout ticket price is "+ticket.getPrice()+" , your number of trips now is "+numOftrips);
        }
        else
        {
            ticket.setCarCode( c[carIndex].getCode());
            ticket.setPrice(100);
            System.out.println("yout ticket price is "+ticket.getPrice());
        }
        
    }
}
class nonSubscriber implements passenger
{
    private double discount;
    private double subscribtionFees;
    private int age;
    private int numOftrips;
    private boolean subscribed;
    ticket ticket;

    public nonSubscriber() {
        subscribed=false;
        ticket=new ticket();
    }
    
    public void subscribe(int age,int numOfTrips)
    {
        if(age>=18)
        {
            
            if(numOfTrips<10)
            {
                try{
                throw new numOfTrips("can't be subscribed. your number of trips must be above 10");
                } catch (numOfTrips ex) {
                    System.out.println(ex.getMessage());
                }
            }
            else if(numOfTrips>=10&&numOfTrips<20){
                subscribtionFees=300;
                this.numOftrips=numOfTrips;
                this.age=age;
                discount=0.5;
                subscribed=true;
                System.out.println("subscribed successfully, your subscribtion fees are "+subscribtionFees);
            }
            else if(numOfTrips>=20&&numOfTrips<30){
                subscribtionFees=500;
                this.numOftrips=numOfTrips;
                this.age=age;
                discount=0.5;
                subscribed=true;
                System.out.println("subscribed successfully, your subscribtion fees are "+subscribtionFees);
            }
            else
            {
                try{
                    throw new numOfTrips("can't be subscribed. your number of trips cannot exceed 30");
                }catch(numOfTrips n){
                    System.out.println(n.getMessage());
                }
            }
        }
        else
            System.out.println("can't be subscribed. subscribers must be above 18");   
    }

    @Override
    public void reserveTicket(car[] c, int choice) {
        int carIndex=choice-1;
        if (subscribed==true)
        {
            ticket.setCarCode( c[carIndex].getCode());
            ticket.setPrice(discount*100);
            numOftrips--;
            if (numOftrips<0)
            {
                try{
                    numOftrips=0;
                    throw new numOfTrips("you don't have avilable trips");
                }catch(numOfTrips n){
                    System.out.println(n.getMessage());
                }
            }
            else
                System.out.println("ticket reserved, yout ticket price is "+ticket.getPrice()+" , your number of trips now is "+numOftrips);
        }
        else
        {
            ticket.setCarCode( c[carIndex].getCode());
            ticket.setPrice(100);
            System.out.println("yout ticket price is "+ticket.getPrice());
        }
    }    
}

abstract class AbstractFactory
{
    public abstract passenger getPassenger(String type, int numoftribs);
}
class passengerFactory extends AbstractFactory{

    @Override
    public passenger getPassenger(String type, int numoftribs) {
        if(type.equals("subscriber"))
            return new subscriber(numoftribs);
        else if (type.equals("non subscriber"))
            return new nonSubscriber();
        return null;
    }
}

class route
{
    private String startlocation;
    private String destination;

    public route(String startlocation, String destination) {
        this.startlocation = startlocation;
        this.destination = destination;
    }

    
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStartlocation(String startlocation) {
        this.startlocation = startlocation;
    }

    public String getDestination() {
        return destination;
    }

    public String getStartlocation() {
        return startlocation;
    }
    
}
class car
{
    private String code;
    private int numOfTrips;
    private int max_capacity;
    private String driverName;
    private route route;

    public car(String code, int numOfTrips, int max_capacity, String driverName, route route) {
        this.code = code;
        this.numOfTrips = numOfTrips;
        this.max_capacity = max_capacity;
        this.driverName = driverName;
        this.route = route;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    public void setNumOfTrips(int numOfTrips) {
        this.numOfTrips = numOfTrips;
    }

    public void setRoute(route route) {
        this.route = route;
    }

    public String getCode() {
        return code;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public int getNumOfTrips() {
        return numOfTrips;
    }

    public route getRoute() {
        return route;
    }
    
}

class ticket
{
    private String carCode;
    private double price;

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCarCode() {
        return carCode;
    }

    public double getPrice() {
        return price;
    }
    
}

class numOfTrips extends Exception
{
    public numOfTrips(String msg) {
        super(msg);
    }     
}