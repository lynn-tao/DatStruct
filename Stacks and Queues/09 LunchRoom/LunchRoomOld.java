// Name: j126
// Date: 1/25

import java.util.*;

public class LunchRoomOld
{
   public static final int TIME = 1080;  //18 hrs * 60 min
   
   public static void main(String[] args)
   {
      PriorityQueue<Customer> queue = new PriorityQueue<Customer>();
      /*  write code for the simulation   */
      System.out.println("LunchRoom(arrival chance is 0.2) with 1 Pqueue / 1 service areas");
      Queue<Customer> service = new LinkedList<Customer>(); 
      //count each type of student
      Queue<Integer> sen = new LinkedList<Integer>(); Queue<Integer> jun = new LinkedList<Integer>(); Queue<Integer> soph = new LinkedList<Integer>(); Queue<Integer> fresh = new LinkedList<Integer>();
   
      for(int i=0; i<=TIME; i++)
      {
         // if customer comes
         if(Math.random()<0.2)
         {
            //instantiate new customer
            Customer c = new Customer(i, timeToOrderAndBeServed(), null);
            int x  = (int)(Math.random()*4)+1;
            if(x==1)
               c.setGrade("A");            
            else if(x==2)
               c.setGrade("B");               
            else if(x==3)
               c.setGrade("C");               
            else
               c.setGrade("D");               
            System.out.println("\n new customer " + c.toString());
            queue.add(c);
                              
            //if service area is empty
            if(service.isEmpty())
            {
               Customer first = queue.remove();
               service.add(first);
               display(i, queue);
               System.out.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
            }
            else 
            {
               Customer placeholder = service.remove();
               //if customer leaves service area
               if(placeholder.getOrder()==1)
               {
                  display(i, queue);
                  System.out.println("    Customer# " + placeholder.toString() + " leaves and his total wait time is " + (i - placeholder.getArrivedAt()));
                  
                  if(c.getGrade().equals("A"))
                     sen.add(i - placeholder.getArrivedAt());
                  if(c.getGrade().equals("B"))
                     jun.add(i - placeholder.getArrivedAt());
                  if(c.getGrade().equals("C"))
                     soph.add(i - placeholder.getArrivedAt());
                  if(c.getGrade().equals("D"))
                     fresh.add(i - placeholder.getArrivedAt());    
               
                  if(!service.isEmpty())
                     service.remove();
                  if(!queue.isEmpty())
                     service.add(queue.remove());
               }
               //decrease minutes of wait time by 1
               else
               {
                  placeholder.setOrder(placeholder.getOrder()-1);
                  service.add(placeholder);
                  display(i, queue);
                  System.out.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
               }
            }
         }
         else  //no new customer
         {
            if(!service.isEmpty())
            {
               Customer placeholder = service.remove();
               if(placeholder.getOrder()==1)
               {
                  display(i, queue);
                  System.out.println("    Customer# " + placeholder.toString() + " leaves and his total wait time is " + (i - placeholder.getArrivedAt()));
                  if(placeholder.getGrade().equals("A"))
                     sen.add(i - placeholder.getArrivedAt());
                  if(placeholder.getGrade().equals("B"))
                     jun.add(i - placeholder.getArrivedAt());
                  if(placeholder.getGrade().equals("C"))
                     soph.add(i - placeholder.getArrivedAt());
                  if(placeholder.getGrade().equals("D"))
                     fresh.add(i - placeholder.getArrivedAt());    
               
                  if(!queue.isEmpty())
                     service.add(queue.remove());
               }
               else
               {
                  placeholder.setOrder(placeholder.getOrder()-1);
                  service.add(placeholder);
                  display(i, queue);
                  System.out.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
               }
            }
            else
            {
               display(i, queue);
               System.out.println("   ServiceArea#1  -1:-1");
            }
         }
      }
      
      
      int i = 1080;
      while(!queue.isEmpty() || !service.isEmpty())
      {        
         i++;
         if(!service.isEmpty())
         {
            Customer placeholder = service.remove();
            if(placeholder.getOrder()==1)
            {
               display(i, queue);
               System.out.println("    Customer# " + placeholder.getArrivedAt() + " leaves and his total wait time is " + (i - placeholder.getArrivedAt()));
            
               if(placeholder.getGrade().equals("A"))
                  sen.add(i - placeholder.getArrivedAt());
               if(placeholder.getGrade().equals("B"))
                  jun.add(i - placeholder.getArrivedAt());
               if(placeholder.getGrade().equals("C"))
                  soph.add(i - placeholder.getArrivedAt());
               if(placeholder.getGrade().equals("D"))
                  fresh.add(i - placeholder.getArrivedAt());  
                     
               if(!queue.isEmpty())
                  service.add(queue.remove());
            }
            else
            {
               placeholder.setOrder(placeholder.getOrder()-1);
               service.add(placeholder);
               display(i, queue);
               System.out.println("   ServiceArea#1  "+ service.peek().getArrivedAt()+":"+service.peek().getOrder());
            }
         }
         else
         {
            if(!queue.isEmpty())
               service.add(queue.remove());
            display(i, queue);
         } 
      }
      
      //calculate statistics
      int time = 0;
      int longestWaitTime = 0;
      int sensize = sen.size();
      while(!sen.isEmpty())
      {
         int x = sen.remove();
         time+=x;
         longestWaitTime = Math.max(longestWaitTime, x);
      }
      
      int t = 0;
      int l = 0;
      int jsize = jun.size();
      while(!jun.isEmpty())
      {
         int x = jun.remove();
         t += x;
         l = Math.max(l, x);
      }
      
      int ti = 0;
      int lo = 0;
      int size = soph.size();
      while(!soph.isEmpty())
      {
         int x = soph.remove();
         ti+=x;
         lo = Math.max(lo, x);
      }
      
      int tt = 0;
      int ll = 0;
      int fsize = fresh.size();
      while(!fresh.isEmpty())
      {
         int x = fresh.remove();
         tt +=x;
         ll = Math.max(ll, x);
      }
      
      System.out.println("Customer\t\tTotal\t\tLongest\t\tAverage Wait");
      /* report the data  */
      System.out.println("Senior \t\t\t " + sensize + "\t\t\t" + longestWaitTime + "\t\t\t" + (double)time/sensize);
      System.out.println("Junior \t\t\t " + jsize + "\t\t\t" + l + "\t\t\t" + (double)t/jsize);
      System.out.println("Sophomore \t\t " + size + "\t\t\t" + lo + "\t\t\t" + (double)ti/size);
      System.out.println("Freshman \t\t " + fsize + "\t\t\t" + ll + "\t\t\t" + (double)tt/fsize);
   
   }
   
   public static void display(int t, PriorityQueue<Customer> q)
   {
      System.out.println(t + ": " + q);	
   }
   public static int timeToOrderAndBeServed()
   {
      return (int)(Math.random() * 6 + 2);
   }
}

class Customer implements Comparable<Customer>
{
   private int time;
   private int serve;
   private String grade;
    //f = D
    //soph = C
    //j =  B
    //sen = A
    /**********************************
       Complete the Customer class with  
       constructor, accessor methods, toString.
    ***********************************/
   public Customer(int t, int order, String id)
   {
      time = t;
      serve = order;
      grade = id;
   }
   public int getArrivedAt()
   {
      return time;
   }
   public int getOrder()
   {
      return serve;
   }
   public void setOrder(int o)
   {
      serve = o;
   }
   public String getGrade()
   {
      return grade;
   }
   public void setGrade(String s)
   {
      grade = s;
   }
   public String toString()
   {
      if(grade.equals("D"))
         return "" + time + ": Fr";
      if(grade.equals("C"))
         return "" + time + ": So";
      if(grade.equals("B"))
         return "" + time + ": Ju";
      else
         return "" + time + ": Se";
   
   }
   public int compareTo(Customer c)
   {
      if(grade.compareTo(c.getGrade())==0)
      {
         if(time < c.getArrivedAt())
            return -1;
         else if(time > c.getArrivedAt())
            return 1;
         else
            return 0;
      }     
      else 
         return grade.compareTo(c.getGrade());   
      
   }
}


/*-------------------------

   LunchRoom(arrival chance is 0.2) with 1 Pqueue / 1 service areas
 0: []
    ServiceArea#1  -1:-1
 1: []
    ServiceArea#1  -1:-1
 
  new customer 2: Se
 2: []
    ServiceArea#1  2:5
 3: []
    ServiceArea#1  2:4
 4: []
    ServiceArea#1  2:3
 5: []
    ServiceArea#1  2:2
 6: []
    ServiceArea#1  2:1
 7: []
     Customer# 2: Se leaves and his total wait time is 5
 8: []
    ServiceArea#1  -1:-1
 9: []
    ServiceArea#1  -1:-1
 
  new customer 10: Se
 10: []
    ServiceArea#1  10:5
 11: []
    ServiceArea#1  10:4
 12: []
    ServiceArea#1  10:3
 13: []
    ServiceArea#1  10:2
 14: []
    ServiceArea#1  10:1
 15: []
     Customer# 10: Se leaves and his total wait time is 5
 16: []
    ServiceArea#1  -1:-1
 17: []
    ServiceArea#1  -1:-1
 
  new customer 18: Fr
 18: []
    ServiceArea#1  18:3
 19: []
    ServiceArea#1  18:2
 
  new customer 20: Fr
 20: [20: Fr]
    ServiceArea#1  18:1
 21: [20: Fr]
     Customer# 18: Fr leaves and his total wait time is 3
 22: []
    ServiceArea#1  20:1
 23: []
     Customer# 20: Fr leaves and his total wait time is 3
 24: []
    ServiceArea#1  -1:-1
 
  new customer 25: Fr
 25: []
    ServiceArea#1  25:2
 26: []
    ServiceArea#1  25:1
 27: []
     Customer# 25: Fr leaves and his total wait time is 2
 28: []
    ServiceArea#1  -1:-1
 29: []
    ServiceArea#1  -1:-1
 30: []
    ServiceArea#1  -1:-1
 31: []
    ServiceArea#1  -1:-1
 32: []
    ServiceArea#1  -1:-1
 33: []
    ServiceArea#1  -1:-1
 34: []
    ServiceArea#1  -1:-1
 
  new customer 35: So
 35: []
    ServiceArea#1  35:7
 36: []
    ServiceArea#1  35:6
 37: []
    ServiceArea#1  35:5
 38: []
    ServiceArea#1  35:4
 
  new customer 39: Ju
 39: [39: Ju]
    ServiceArea#1  35:3
 40: [39: Ju]
    ServiceArea#1  35:2
 41: [39: Ju]
    ServiceArea#1  35:1
 
  new customer 42: So
 42: [39: Ju, 42: So]
     Customer# 35: So leaves and his total wait time is 7
 43: [42: So]
    ServiceArea#1  39:3
 44: [42: So]
    ServiceArea#1  39:2
 
  new customer 45: Fr
 45: [42: So, 45: Fr]
    ServiceArea#1  39:1
 46: [42: So, 45: Fr]
     Customer# 39: Ju leaves and his total wait time is 7
 47: [45: Fr]
    ServiceArea#1  42:6
 48: [45: Fr]
    ServiceArea#1  42:5
 49: [45: Fr]
    ServiceArea#1  42:4
 50: [45: Fr]
    ServiceArea#1  42:3
 51: [45: Fr]
    ServiceArea#1  42:2
 52: [45: Fr]
    ServiceArea#1  42:1
 53: [45: Fr]
     Customer# 42: So leaves and his total wait time is 11
 54: []
    ServiceArea#1  45:2
 55: []
    ServiceArea#1  45:1
 56: []
     Customer# 45: Fr leaves and his total wait time is 11
 57: []
    ServiceArea#1  -1:-1
 58: []
    ServiceArea#1  -1:-1
 59: []
    ServiceArea#1  -1:-1
 60: []
    ServiceArea#1  -1:-1
 61: []
    ServiceArea#1  -1:-1
 
  new customer 62: Fr
 62: []
    ServiceArea#1  62:3
 
  new customer 63: So
 63: [63: So]
    ServiceArea#1  62:2
 64: [63: So]
    ServiceArea#1  62:1
 65: [63: So]
     Customer# 62: Fr leaves and his total wait time is 3
 66: []
    ServiceArea#1  63:6
 67: []
    ServiceArea#1  63:5
 
  new customer 68: Fr
 68: [68: Fr]
    ServiceArea#1  63:4
 
  new customer 69: Ju
 69: [69: Ju, 68: Fr]
    ServiceArea#1  63:3
 70: [69: Ju, 68: Fr]
    ServiceArea#1  63:2
 71: [69: Ju, 68: Fr]
    ServiceArea#1  63:1
 72: [69: Ju, 68: Fr]
     Customer# 63: So leaves and his total wait time is 9
 73: [68: Fr]
    ServiceArea#1  69:5
 74: [68: Fr]
    ServiceArea#1  69:4
 75: [68: Fr]
    ServiceArea#1  69:3
 76: [68: Fr]
    ServiceArea#1  69:2
 77: [68: Fr]
    ServiceArea#1  69:1
 78: [68: Fr]
     Customer# 69: Ju leaves and his total wait time is 9
 79: []
    ServiceArea#1  68:2
 80: []
    ServiceArea#1  68:1
 81: []
     Customer# 68: Fr leaves and his total wait time is 13
 82: []
    ServiceArea#1  -1:-1
 83: []
    ServiceArea#1  -1:-1
 
  new customer 84: Fr
 84: []
    ServiceArea#1  84:7
 85: []
    ServiceArea#1  84:6
 86: []
    ServiceArea#1  84:5
 87: []
    ServiceArea#1  84:4
 88: []
    ServiceArea#1  84:3
 89: []
    ServiceArea#1  84:2
 90: []
    ServiceArea#1  84:1
 
  new customer 91: Fr
 91: [91: Fr]
     Customer# 84: Fr leaves and his total wait time is 7
 92: []
    ServiceArea#1  91:3
 93: []
    ServiceArea#1  91:2
 94: []
    ServiceArea#1  91:1
 95: []
     Customer# 91: Fr leaves and his total wait time is 4
 96: []
    ServiceArea#1  -1:-1
 
  new customer 97: Ju
 97: []
    ServiceArea#1  97:5
 98: []
    ServiceArea#1  97:4
 99: []
    ServiceArea#1  97:3
 100: []
    ServiceArea#1  97:2
 101: []
    ServiceArea#1  97:1
 102: []
     Customer# 97: Ju leaves and his total wait time is 5
 103: []
    ServiceArea#1  -1:-1
 104: []
    ServiceArea#1  -1:-1
 105: []
    ServiceArea#1  -1:-1
 106: []
    ServiceArea#1  -1:-1
 
  new customer 107: Fr
 107: []
    ServiceArea#1  107:3
 108: []
    ServiceArea#1  107:2
 
  new customer 109: Fr
 109: [109: Fr]
    ServiceArea#1  107:1
 110: [109: Fr]
     Customer# 107: Fr leaves and his total wait time is 3
 111: []
    ServiceArea#1  109:6
 112: []
    ServiceArea#1  109:5
 113: []
    ServiceArea#1  109:4
 114: []
    ServiceArea#1  109:3
 115: []
    ServiceArea#1  109:2
 116: []
    ServiceArea#1  109:1
 117: []
     Customer# 109: Fr leaves and his total wait time is 8
 118: []
    ServiceArea#1  -1:-1
 
  new customer 119: Fr
 119: []
    ServiceArea#1  119:6
 120: []
    ServiceArea#1  119:5
 121: []
    ServiceArea#1  119:4
 122: []
    ServiceArea#1  119:3
 123: []
    ServiceArea#1  119:2
 124: []
    ServiceArea#1  119:1
 125: []
     Customer# 119: Fr leaves and his total wait time is 6
 
  new customer 126: Fr
 126: []
    ServiceArea#1  126:5
 127: []
    ServiceArea#1  126:4
 128: []
    ServiceArea#1  126:3
 129: []
    ServiceArea#1  126:2
 130: []
    ServiceArea#1  126:1
 
  new customer 131: Se
 131: [131: Se]
     Customer# 126: Fr leaves and his total wait time is 5
 132: []
    ServiceArea#1  131:6
 133: []
    ServiceArea#1  131:5
 134: []
    ServiceArea#1  131:4
 135: []
    ServiceArea#1  131:3
 136: []
    ServiceArea#1  131:2
 
  new customer 137: Fr
 137: [137: Fr]
    ServiceArea#1  131:1
 138: [137: Fr]
     Customer# 131: Se leaves and his total wait time is 7
 139: []
    ServiceArea#1  137:2
 140: []
    ServiceArea#1  137:1
 141: []
     Customer# 137: Fr leaves and his total wait time is 4
 142: []
    ServiceArea#1  -1:-1
 143: []
    ServiceArea#1  -1:-1
 144: []
    ServiceArea#1  -1:-1
 145: []
    ServiceArea#1  -1:-1
 146: []
    ServiceArea#1  -1:-1
 
  new customer 147: Se
 147: []
    ServiceArea#1  147:3
 148: []
    ServiceArea#1  147:2
 149: []
    ServiceArea#1  147:1
 150: []
     Customer# 147: Se leaves and his total wait time is 3
 151: []
    ServiceArea#1  -1:-1
 152: []
    ServiceArea#1  -1:-1
 153: []
    ServiceArea#1  -1:-1
 
  new customer 154: Fr
 154: []
    ServiceArea#1  154:3
 155: []
    ServiceArea#1  154:2
 156: []
    ServiceArea#1  154:1
 
  new customer 157: Ju
 157: [157: Ju]
     Customer# 154: Fr leaves and his total wait time is 3
 158: []
    ServiceArea#1  157:3
 159: []
    ServiceArea#1  157:2
 160: []
    ServiceArea#1  157:1
 161: []
     Customer# 157: Ju leaves and his total wait time is 4
 
  new customer 162: Se
 162: []
    ServiceArea#1  162:3
 163: []
    ServiceArea#1  162:2
 164: []
    ServiceArea#1  162:1
 165: []
     Customer# 162: Se leaves and his total wait time is 3
 166: []
    ServiceArea#1  -1:-1
 167: []
    ServiceArea#1  -1:-1
 
  new customer 168: Fr
 168: []
    ServiceArea#1  168:3
 169: []
    ServiceArea#1  168:2
 170: []
    ServiceArea#1  168:1
 
  new customer 171: So
 171: [171: So]
     Customer# 168: Fr leaves and his total wait time is 3
 
  new customer 172: Fr
 172: [172: Fr]
    ServiceArea#1  171:5
 173: [172: Fr]
    ServiceArea#1  171:4
 174: [172: Fr]
    ServiceArea#1  171:3
 175: [172: Fr]
    ServiceArea#1  171:2
 176: [172: Fr]
    ServiceArea#1  171:1
 
  new customer 177: Se
 177: [177: Se, 172: Fr]
     Customer# 171: So leaves and his total wait time is 6
 178: [172: Fr]
    ServiceArea#1  177:2
 179: [172: Fr]
    ServiceArea#1  177:1
 180: [172: Fr]
     Customer# 177: Se leaves and his total wait time is 3
 181: []
    ServiceArea#1  172:2
 182: []
    ServiceArea#1  172:1
 
  new customer 183: Fr
 183: [183: Fr]
     Customer# 172: Fr leaves and his total wait time is 11
 184: []
    ServiceArea#1  183:2
 185: []
    ServiceArea#1  183:1
 186: []
     Customer# 183: Fr leaves and his total wait time is 3
 
  new customer 187: So
 187: []
    ServiceArea#1  187:7
 
  new customer 188: Fr
 188: [188: Fr]
    ServiceArea#1  187:6
 189: [188: Fr]
    ServiceArea#1  187:5
 190: [188: Fr]
    ServiceArea#1  187:4
 191: [188: Fr]
    ServiceArea#1  187:3
 
  new customer 192: Fr
 192: [188: Fr, 192: Fr]
    ServiceArea#1  187:2
 193: [188: Fr, 192: Fr]
    ServiceArea#1  187:1
 194: [188: Fr, 192: Fr]
     Customer# 187: So leaves and his total wait time is 7
 195: [192: Fr]
    ServiceArea#1  188:2
 
  new customer 196: Ju
 196: [196: Ju, 192: Fr]
    ServiceArea#1  188:1
 197: [196: Ju, 192: Fr]
     Customer# 188: Fr leaves and his total wait time is 9
 
  new customer 198: Ju
 198: [198: Ju, 192: Fr]
    ServiceArea#1  196:4
 199: [198: Ju, 192: Fr]
    ServiceArea#1  196:3
 200: [198: Ju, 192: Fr]
    ServiceArea#1  196:2
 201: [198: Ju, 192: Fr]
    ServiceArea#1  196:1
 202: [198: Ju, 192: Fr]
     Customer# 196: Ju leaves and his total wait time is 6
 203: [192: Fr]
    ServiceArea#1  198:5
 204: [192: Fr]
    ServiceArea#1  198:4
 205: [192: Fr]
    ServiceArea#1  198:3
 206: [192: Fr]
    ServiceArea#1  198:2
 207: [192: Fr]
    ServiceArea#1  198:1
 208: [192: Fr]
     Customer# 198: Ju leaves and his total wait time is 10
 209: []
    ServiceArea#1  192:6
 210: []
    ServiceArea#1  192:5
 211: []
    ServiceArea#1  192:4
 
  new customer 212: Fr
 212: [212: Fr]
    ServiceArea#1  192:3
 213: [212: Fr]
    ServiceArea#1  192:2
 214: [212: Fr]
    ServiceArea#1  192:1
 
  new customer 215: Se
 215: [215: Se, 212: Fr]
     Customer# 192: Fr leaves and his total wait time is 23
 216: [212: Fr]
    ServiceArea#1  215:3
 217: [212: Fr]
    ServiceArea#1  215:2
 218: [212: Fr]
    ServiceArea#1  215:1
 
  new customer 219: Ju
 219: [219: Ju, 212: Fr]
     Customer# 215: Se leaves and his total wait time is 4
 220: [212: Fr]
    ServiceArea#1  219:4
 221: [212: Fr]
    ServiceArea#1  219:3
 222: [212: Fr]
    ServiceArea#1  219:2
 
  new customer 223: Fr
 223: [212: Fr, 223: Fr]
    ServiceArea#1  219:1
 
  new customer 224: So
 224: [224: So, 223: Fr, 212: Fr]
     Customer# 219: Ju leaves and his total wait time is 5
 
  new customer 225: Ju
 225: [225: Ju, 223: Fr, 212: Fr]
    ServiceArea#1  224:6
 
  new customer 226: Se
 226: [226: Se, 225: Ju, 212: Fr, 223: Fr]
    ServiceArea#1  224:5
 227: [226: Se, 225: Ju, 212: Fr, 223: Fr]
    ServiceArea#1  224:4
 228: [226: Se, 225: Ju, 212: Fr, 223: Fr]
    ServiceArea#1  224:3
 
  new customer 229: Se
 229: [226: Se, 229: Se, 212: Fr, 223: Fr, 225: Ju]
    ServiceArea#1  224:2
 230: [226: Se, 229: Se, 212: Fr, 223: Fr, 225: Ju]
    ServiceArea#1  224:1
 231: [226: Se, 229: Se, 212: Fr, 223: Fr, 225: Ju]
     Customer# 224: So leaves and his total wait time is 7
 232: [229: Se, 225: Ju, 212: Fr, 223: Fr]
    ServiceArea#1  226:5
 233: [229: Se, 225: Ju, 212: Fr, 223: Fr]
    ServiceArea#1  226:4
 234: [229: Se, 225: Ju, 212: Fr, 223: Fr]
    ServiceArea#1  226:3
 235: [229: Se, 225: Ju, 212: Fr, 223: Fr]
    ServiceArea#1  226:2
 236: [229: Se, 225: Ju, 212: Fr, 223: Fr]
    ServiceArea#1  226:1
 237: [229: Se, 225: Ju, 212: Fr, 223: Fr]
     Customer# 226: Se leaves and his total wait time is 11
 238: [225: Ju, 223: Fr, 212: Fr]
    ServiceArea#1  229:3
 
  new customer 239: So
 239: [225: Ju, 239: So, 212: Fr, 223: Fr]
    ServiceArea#1  229:2
 240: [225: Ju, 239: So, 212: Fr, 223: Fr]
    ServiceArea#1  229:1
 241: [225: Ju, 239: So, 212: Fr, 223: Fr]
     Customer# 229: Se leaves and his total wait time is 12
 242: [239: So, 223: Fr, 212: Fr]
    ServiceArea#1  225:2
 243: [239: So, 223: Fr, 212: Fr]
    ServiceArea#1  225:1
 
  new customer 244: Fr
 244: [239: So, 223: Fr, 212: Fr, 244: Fr]
     Customer# 225: Ju leaves and his total wait time is 19
 
  new customer 245: Se
 245: [245: Se, 212: Fr, 244: Fr, 223: Fr]
    ServiceArea#1  239:3
 246: [245: Se, 212: Fr, 244: Fr, 223: Fr]
    ServiceArea#1  239:2
 247: [245: Se, 212: Fr, 244: Fr, 223: Fr]
    ServiceArea#1  239:1
 248: [245: Se, 212: Fr, 244: Fr, 223: Fr]
     Customer# 239: So leaves and his total wait time is 9
 249: [212: Fr, 223: Fr, 244: Fr]
    ServiceArea#1  245:4
 
  new customer 250: Fr
 250: [212: Fr, 223: Fr, 244: Fr, 250: Fr]
    ServiceArea#1  245:3
 
  new customer 251: Ju
 251: [251: Ju, 212: Fr, 244: Fr, 250: Fr, 223: Fr]
    ServiceArea#1  245:2
 252: [251: Ju, 212: Fr, 244: Fr, 250: Fr, 223: Fr]
    ServiceArea#1  245:1
 253: [251: Ju, 212: Fr, 244: Fr, 250: Fr, 223: Fr]
     Customer# 245: Se leaves and his total wait time is 8
 254: [212: Fr, 223: Fr, 244: Fr, 250: Fr]
    ServiceArea#1  251:1
 255: [212: Fr, 223: Fr, 244: Fr, 250: Fr]
     Customer# 251: Ju leaves and his total wait time is 4
 256: [223: Fr, 250: Fr, 244: Fr]
    ServiceArea#1  212:1
 257: [223: Fr, 250: Fr, 244: Fr]
     Customer# 212: Fr leaves and his total wait time is 45
 258: [244: Fr, 250: Fr]
    ServiceArea#1  223:2
 259: [244: Fr, 250: Fr]
    ServiceArea#1  223:1
 260: [244: Fr, 250: Fr]
     Customer# 223: Fr leaves and his total wait time is 37
 261: [250: Fr]
    ServiceArea#1  244:3
 262: [250: Fr]
    ServiceArea#1  244:2
 263: [250: Fr]
    ServiceArea#1  244:1
 
  new customer 264: Se
 264: [264: Se, 250: Fr]
     Customer# 244: Fr leaves and his total wait time is 20
 265: [250: Fr]
    ServiceArea#1  264:5
 266: [250: Fr]
    ServiceArea#1  264:4
 267: [250: Fr]
    ServiceArea#1  264:3
 268: [250: Fr]
    ServiceArea#1  264:2
 269: [250: Fr]
    ServiceArea#1  264:1
 270: [250: Fr]
     Customer# 264: Se leaves and his total wait time is 6
 271: []
    ServiceArea#1  250:6
 272: []
    ServiceArea#1  250:5
 273: []
    ServiceArea#1  250:4
 274: []
    ServiceArea#1  250:3
 275: []
    ServiceArea#1  250:2
 
  new customer 276: So
 276: [276: So]
    ServiceArea#1  250:1
 277: [276: So]
     Customer# 250: Fr leaves and his total wait time is 27
 278: []
    ServiceArea#1  276:4
 279: []
    ServiceArea#1  276:3
 
  new customer 280: Ju
 280: [280: Ju]
    ServiceArea#1  276:2
 281: [280: Ju]
    ServiceArea#1  276:1
 282: [280: Ju]
     Customer# 276: So leaves and his total wait time is 6
 283: []
    ServiceArea#1  280:2
 284: []
    ServiceArea#1  280:1
 285: []
     Customer# 280: Ju leaves and his total wait time is 5
 286: []
    ServiceArea#1  -1:-1
 287: []
    ServiceArea#1  -1:-1
 288: []
    ServiceArea#1  -1:-1
 289: []
    ServiceArea#1  -1:-1
 
  new customer 290: Fr
 290: []
    ServiceArea#1  290:6
 291: []
    ServiceArea#1  290:5
 292: []
    ServiceArea#1  290:4
 
  new customer 293: Ju
 293: [293: Ju]
    ServiceArea#1  290:3
 
  new customer 294: Ju
 294: [293: Ju, 294: Ju]
    ServiceArea#1  290:2
 295: [293: Ju, 294: Ju]
    ServiceArea#1  290:1
 296: [293: Ju, 294: Ju]
     Customer# 290: Fr leaves and his total wait time is 6
 297: [294: Ju]
    ServiceArea#1  293:3
 298: [294: Ju]
    ServiceArea#1  293:2
 299: [294: Ju]
    ServiceArea#1  293:1
 300: [294: Ju]
     Customer# 293: Ju leaves and his total wait time is 7
 301: []
    ServiceArea#1  294:5
 302: []
    ServiceArea#1  294:4
 303: []
    ServiceArea#1  294:3
 304: []
    ServiceArea#1  294:2
 305: []
    ServiceArea#1  294:1
 
  new customer 306: So
 306: [306: So]
     Customer# 294: Ju leaves and his total wait time is 12
 307: []
    ServiceArea#1  306:5
 308: []
    ServiceArea#1  306:4
 309: []
    ServiceArea#1  306:3
 310: []
    ServiceArea#1  306:2
 311: []
    ServiceArea#1  306:1
 
  new customer 312: Fr
 312: [312: Fr]
     Customer# 306: So leaves and his total wait time is 6
 313: []
    ServiceArea#1  312:6
 
  new customer 314: Ju
 314: [314: Ju]
    ServiceArea#1  312:5
 315: [314: Ju]
    ServiceArea#1  312:4
 
  new customer 316: So
 316: [314: Ju, 316: So]
    ServiceArea#1  312:3
 
  new customer 317: Fr
 317: [314: Ju, 316: So, 317: Fr]
    ServiceArea#1  312:2
 
  new customer 318: Ju
 318: [314: Ju, 318: Ju, 317: Fr, 316: So]
    ServiceArea#1  312:1
 319: [314: Ju, 318: Ju, 317: Fr, 316: So]
     Customer# 312: Fr leaves and his total wait time is 7
 320: [318: Ju, 316: So, 317: Fr]
    ServiceArea#1  314:6
 
  new customer 321: Ju
 321: [318: Ju, 321: Ju, 317: Fr, 316: So]
    ServiceArea#1  314:5
 322: [318: Ju, 321: Ju, 317: Fr, 316: So]
    ServiceArea#1  314:4
 323: [318: Ju, 321: Ju, 317: Fr, 316: So]
    ServiceArea#1  314:3
 
  new customer 324: Fr
 324: [318: Ju, 321: Ju, 317: Fr, 316: So, 324: Fr]
    ServiceArea#1  314:2
 
  new customer 325: Ju
 325: [318: Ju, 321: Ju, 325: Ju, 316: So, 324: Fr, 317: Fr]
    ServiceArea#1  314:1
 326: [318: Ju, 321: Ju, 325: Ju, 316: So, 324: Fr, 317: Fr]
     Customer# 314: Ju leaves and his total wait time is 12
 327: [321: Ju, 316: So, 325: Ju, 317: Fr, 324: Fr]
    ServiceArea#1  318:5
 328: [321: Ju, 316: So, 325: Ju, 317: Fr, 324: Fr]
    ServiceArea#1  318:4
 329: [321: Ju, 316: So, 325: Ju, 317: Fr, 324: Fr]
    ServiceArea#1  318:3
 330: [321: Ju, 316: So, 325: Ju, 317: Fr, 324: Fr]
    ServiceArea#1  318:2
 331: [321: Ju, 316: So, 325: Ju, 317: Fr, 324: Fr]
    ServiceArea#1  318:1
 332: [321: Ju, 316: So, 325: Ju, 317: Fr, 324: Fr]
     Customer# 318: Ju leaves and his total wait time is 14
 333: [325: Ju, 316: So, 324: Fr, 317: Fr]
    ServiceArea#1  321:2
 334: [325: Ju, 316: So, 324: Fr, 317: Fr]
    ServiceArea#1  321:1
 335: [325: Ju, 316: So, 324: Fr, 317: Fr]
     Customer# 321: Ju leaves and his total wait time is 14
 336: [316: So, 317: Fr, 324: Fr]
    ServiceArea#1  325:5
 
  new customer 337: So
 337: [316: So, 337: So, 324: Fr, 317: Fr]
    ServiceArea#1  325:4
 338: [316: So, 337: So, 324: Fr, 317: Fr]
    ServiceArea#1  325:3
 339: [316: So, 337: So, 324: Fr, 317: Fr]
    ServiceArea#1  325:2
 340: [316: So, 337: So, 324: Fr, 317: Fr]
    ServiceArea#1  325:1
 
  new customer 341: So
 341: [316: So, 337: So, 324: Fr, 317: Fr, 341: So]
     Customer# 325: Ju leaves and his total wait time is 16
 
  new customer 342: Fr
 342: [337: So, 341: So, 324: Fr, 317: Fr, 342: Fr]
    ServiceArea#1  316:3
 343: [337: So, 341: So, 324: Fr, 317: Fr, 342: Fr]
    ServiceArea#1  316:2
 
  new customer 344: Ju
 344: [344: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  316:1
 345: [344: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
     Customer# 316: So leaves and his total wait time is 29
 346: [337: So, 341: So, 324: Fr, 317: Fr, 342: Fr]
    ServiceArea#1  344:5
 347: [337: So, 341: So, 324: Fr, 317: Fr, 342: Fr]
    ServiceArea#1  344:4
 348: [337: So, 341: So, 324: Fr, 317: Fr, 342: Fr]
    ServiceArea#1  344:3
 349: [337: So, 341: So, 324: Fr, 317: Fr, 342: Fr]
    ServiceArea#1  344:2
 
  new customer 350: Se
 350: [350: Se, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  344:1
 
  new customer 351: Ju
 351: [350: Se, 341: So, 351: Ju, 317: Fr, 342: Fr, 324: Fr, 337: So]
     Customer# 344: Ju leaves and his total wait time is 7
 352: [351: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  350:5
 353: [351: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  350:4
 354: [351: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  350:3
 355: [351: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  350:2
 356: [351: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  350:1
 
  new customer 357: Ju
 357: [351: Ju, 341: So, 357: Ju, 317: Fr, 342: Fr, 324: Fr, 337: So]
     Customer# 350: Se leaves and his total wait time is 7
 358: [357: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  351:4
 
  new customer 359: Ju
 359: [357: Ju, 341: So, 359: Ju, 317: Fr, 342: Fr, 324: Fr, 337: So]
    ServiceArea#1  351:3
 360: [357: Ju, 341: So, 359: Ju, 317: Fr, 342: Fr, 324: Fr, 337: So]
    ServiceArea#1  351:2
 
  new customer 361: Se
 361: [361: Se, 357: Ju, 359: Ju, 341: So, 342: Fr, 324: Fr, 337: So, 317: Fr]
    ServiceArea#1  351:1
 362: [361: Se, 357: Ju, 359: Ju, 341: So, 342: Fr, 324: Fr, 337: So, 317: Fr]
     Customer# 351: Ju leaves and his total wait time is 11
 363: [357: Ju, 341: So, 359: Ju, 317: Fr, 342: Fr, 324: Fr, 337: So]
    ServiceArea#1  361:3
 364: [357: Ju, 341: So, 359: Ju, 317: Fr, 342: Fr, 324: Fr, 337: So]
    ServiceArea#1  361:2
 365: [357: Ju, 341: So, 359: Ju, 317: Fr, 342: Fr, 324: Fr, 337: So]
    ServiceArea#1  361:1
 366: [357: Ju, 341: So, 359: Ju, 317: Fr, 342: Fr, 324: Fr, 337: So]
     Customer# 361: Se leaves and his total wait time is 5
 367: [359: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  357:3
 368: [359: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  357:2
 369: [359: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
    ServiceArea#1  357:1
 370: [359: Ju, 341: So, 337: So, 317: Fr, 342: Fr, 324: Fr]
     Customer# 357: Ju leaves and his total wait time is 13
 371: [337: So, 341: So, 324: Fr, 317: Fr, 342: Fr]
    ServiceArea#1  359:2
 372: [337: So, 341: So, 324: Fr, 317: Fr, 342: Fr]
    ServiceArea#1  359:1
 373: [337: So, 341: So, 324: Fr, 317: Fr, 342: Fr]
     Customer# 359: Ju leaves and his total wait time is 14
 
  new customer 374: Se
 374: [374: Se, 341: So, 324: Fr, 342: Fr, 317: Fr]
    ServiceArea#1  337:2
 375: [374: Se, 341: So, 324: Fr, 342: Fr, 317: Fr]
    ServiceArea#1  337:1
 
  new customer 376: Ju
 376: [374: Se, 341: So, 376: Ju, 342: Fr, 317: Fr, 324: Fr]
     Customer# 337: So leaves and his total wait time is 39
 377: [376: Ju, 341: So, 324: Fr, 342: Fr, 317: Fr]
    ServiceArea#1  374:6
 378: [376: Ju, 341: So, 324: Fr, 342: Fr, 317: Fr]
    ServiceArea#1  374:5
 379: [376: Ju, 341: So, 324: Fr, 342: Fr, 317: Fr]
    ServiceArea#1  374:4
 
  new customer 380: Fr
 380: [376: Ju, 341: So, 324: Fr, 342: Fr, 317: Fr, 380: Fr]
    ServiceArea#1  374:3
 381: [376: Ju, 341: So, 324: Fr, 342: Fr, 317: Fr, 380: Fr]
    ServiceArea#1  374:2
 382: [376: Ju, 341: So, 324: Fr, 342: Fr, 317: Fr, 380: Fr]
    ServiceArea#1  374:1
 383: [376: Ju, 341: So, 324: Fr, 342: Fr, 317: Fr, 380: Fr]
     Customer# 374: Se leaves and his total wait time is 9
 384: [341: So, 317: Fr, 324: Fr, 342: Fr, 380: Fr]
    ServiceArea#1  376:5
 385: [341: So, 317: Fr, 324: Fr, 342: Fr, 380: Fr]
    ServiceArea#1  376:4
 386: [341: So, 317: Fr, 324: Fr, 342: Fr, 380: Fr]
    ServiceArea#1  376:3
 387: [341: So, 317: Fr, 324: Fr, 342: Fr, 380: Fr]
    ServiceArea#1  376:2
 388: [341: So, 317: Fr, 324: Fr, 342: Fr, 380: Fr]
    ServiceArea#1  376:1
 389: [341: So, 317: Fr, 324: Fr, 342: Fr, 380: Fr]
     Customer# 376: Ju leaves and his total wait time is 13
 390: [317: Fr, 342: Fr, 324: Fr, 380: Fr]
    ServiceArea#1  341:1
 391: [317: Fr, 342: Fr, 324: Fr, 380: Fr]
     Customer# 341: So leaves and his total wait time is 50
 392: [324: Fr, 342: Fr, 380: Fr]
    ServiceArea#1  317:1
 393: [324: Fr, 342: Fr, 380: Fr]
     Customer# 317: Fr leaves and his total wait time is 76
 
  new customer 394: Ju
 394: [394: Ju, 380: Fr, 342: Fr]
    ServiceArea#1  324:3
 395: [394: Ju, 380: Fr, 342: Fr]
    ServiceArea#1  324:2
 396: [394: Ju, 380: Fr, 342: Fr]
    ServiceArea#1  324:1
 397: [394: Ju, 380: Fr, 342: Fr]
     Customer# 324: Fr leaves and his total wait time is 73
 398: [342: Fr, 380: Fr]
    ServiceArea#1  394:5
 
  new customer 399: Se
 399: [399: Se, 380: Fr, 342: Fr]
    ServiceArea#1  394:4
 
  new customer 400: Ju
 400: [399: Se, 400: Ju, 342: Fr, 380: Fr]
    ServiceArea#1  394:3
 401: [399: Se, 400: Ju, 342: Fr, 380: Fr]
    ServiceArea#1  394:2
 402: [399: Se, 400: Ju, 342: Fr, 380: Fr]
    ServiceArea#1  394:1
 403: [399: Se, 400: Ju, 342: Fr, 380: Fr]
     Customer# 394: Ju leaves and his total wait time is 9
 404: [400: Ju, 380: Fr, 342: Fr]
    ServiceArea#1  399:6
 405: [400: Ju, 380: Fr, 342: Fr]
    ServiceArea#1  399:5
 
  new customer 406: Fr
 406: [400: Ju, 380: Fr, 342: Fr, 406: Fr]
    ServiceArea#1  399:4
 
  new customer 407: Fr
 407: [400: Ju, 380: Fr, 342: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  399:3
 408: [400: Ju, 380: Fr, 342: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  399:2
 409: [400: Ju, 380: Fr, 342: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  399:1
 410: [400: Ju, 380: Fr, 342: Fr, 406: Fr, 407: Fr]
     Customer# 399: Se leaves and his total wait time is 11
 
  new customer 411: So
 411: [411: So, 342: Fr, 407: Fr, 406: Fr, 380: Fr]
    ServiceArea#1  400:4
 412: [411: So, 342: Fr, 407: Fr, 406: Fr, 380: Fr]
    ServiceArea#1  400:3
 413: [411: So, 342: Fr, 407: Fr, 406: Fr, 380: Fr]
    ServiceArea#1  400:2
 414: [411: So, 342: Fr, 407: Fr, 406: Fr, 380: Fr]
    ServiceArea#1  400:1
 415: [411: So, 342: Fr, 407: Fr, 406: Fr, 380: Fr]
     Customer# 400: Ju leaves and his total wait time is 15
 416: [342: Fr, 380: Fr, 407: Fr, 406: Fr]
    ServiceArea#1  411:5
 417: [342: Fr, 380: Fr, 407: Fr, 406: Fr]
    ServiceArea#1  411:4
 418: [342: Fr, 380: Fr, 407: Fr, 406: Fr]
    ServiceArea#1  411:3
 419: [342: Fr, 380: Fr, 407: Fr, 406: Fr]
    ServiceArea#1  411:2
 420: [342: Fr, 380: Fr, 407: Fr, 406: Fr]
    ServiceArea#1  411:1
 421: [342: Fr, 380: Fr, 407: Fr, 406: Fr]
     Customer# 411: So leaves and his total wait time is 10
 422: [380: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  342:6
 423: [380: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  342:5
 424: [380: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  342:4
 425: [380: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  342:3
 
  new customer 426: So
 426: [426: So, 380: Fr, 407: Fr, 406: Fr]
    ServiceArea#1  342:2
 427: [426: So, 380: Fr, 407: Fr, 406: Fr]
    ServiceArea#1  342:1
 428: [426: So, 380: Fr, 407: Fr, 406: Fr]
     Customer# 342: Fr leaves and his total wait time is 86
 429: [380: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  426:4
 430: [380: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  426:3
 431: [380: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  426:2
 432: [380: Fr, 406: Fr, 407: Fr]
    ServiceArea#1  426:1
 
  new customer 433: Fr
 433: [380: Fr, 406: Fr, 407: Fr, 433: Fr]
     Customer# 426: So leaves and his total wait time is 7
 434: [406: Fr, 433: Fr, 407: Fr]
    ServiceArea#1  380:1
 
  new customer 435: Se
 435: [435: Se, 406: Fr, 407: Fr, 433: Fr]
     Customer# 380: Fr leaves and his total wait time is 55
 436: [406: Fr, 433: Fr, 407: Fr]
    ServiceArea#1  435:4
 437: [406: Fr, 433: Fr, 407: Fr]
    ServiceArea#1  435:3
 438: [406: Fr, 433: Fr, 407: Fr]
    ServiceArea#1  435:2
 439: [406: Fr, 433: Fr, 407: Fr]
    ServiceArea#1  435:1
 440: [406: Fr, 433: Fr, 407: Fr]
     Customer# 435: Se leaves and his total wait time is 5
 441: [407: Fr, 433: Fr]
    ServiceArea#1  406:2
 442: [407: Fr, 433: Fr]
    ServiceArea#1  406:1
 443: [407: Fr, 433: Fr]
     Customer# 406: Fr leaves and his total wait time is 37
 444: [433: Fr]
    ServiceArea#1  407:3
 445: [433: Fr]
    ServiceArea#1  407:2
 
  new customer 446: So
 446: [446: So, 433: Fr]
    ServiceArea#1  407:1
 447: [446: So, 433: Fr]
     Customer# 407: Fr leaves and his total wait time is 40
 
  new customer 448: Fr
 448: [433: Fr, 448: Fr]
    ServiceArea#1  446:2
 449: [433: Fr, 448: Fr]
    ServiceArea#1  446:1
 450: [433: Fr, 448: Fr]
     Customer# 446: So leaves and his total wait time is 4
 451: [448: Fr]
    ServiceArea#1  433:4
 452: [448: Fr]
    ServiceArea#1  433:3
 453: [448: Fr]
    ServiceArea#1  433:2
 454: [448: Fr]
    ServiceArea#1  433:1
 455: [448: Fr]
     Customer# 433: Fr leaves and his total wait time is 22
 456: []
    ServiceArea#1  448:5
 457: []
    ServiceArea#1  448:4
 458: []
    ServiceArea#1  448:3
 459: []
    ServiceArea#1  448:2
 460: []
    ServiceArea#1  448:1
 461: []
     Customer# 448: Fr leaves and his total wait time is 13
 462: []
    ServiceArea#1  -1:-1
 
  new customer 463: Fr
 463: []
    ServiceArea#1  463:5
 464: []
    ServiceArea#1  463:4
 
  new customer 465: Fr
 465: [465: Fr]
    ServiceArea#1  463:3
 466: [465: Fr]
    ServiceArea#1  463:2
 467: [465: Fr]
    ServiceArea#1  463:1
 468: [465: Fr]
     Customer# 463: Fr leaves and his total wait time is 5
 
  new customer 469: Se
 469: [469: Se]
    ServiceArea#1  465:2
 470: [469: Se]
    ServiceArea#1  465:1
 
  new customer 471: So
 471: [469: Se, 471: So]
     Customer# 465: Fr leaves and his total wait time is 6
 472: [471: So]
    ServiceArea#1  469:1
 
  new customer 473: Fr
 473: [471: So, 473: Fr]
     Customer# 469: Se leaves and his total wait time is 4
 474: [473: Fr]
    ServiceArea#1  471:4
 475: [473: Fr]
    ServiceArea#1  471:3
 476: [473: Fr]
    ServiceArea#1  471:2
 477: [473: Fr]
    ServiceArea#1  471:1
 478: [473: Fr]
     Customer# 471: So leaves and his total wait time is 7
 479: []
    ServiceArea#1  473:4
 480: []
    ServiceArea#1  473:3
 
  new customer 481: So
 481: [481: So]
    ServiceArea#1  473:2
 482: [481: So]
    ServiceArea#1  473:1
 483: [481: So]
     Customer# 473: Fr leaves and his total wait time is 10
 484: []
    ServiceArea#1  481:3
 
  new customer 485: Ju
 485: [485: Ju]
    ServiceArea#1  481:2
 486: [485: Ju]
    ServiceArea#1  481:1
 487: [485: Ju]
     Customer# 481: So leaves and his total wait time is 6
 488: []
    ServiceArea#1  485:3
 489: []
    ServiceArea#1  485:2
 490: []
    ServiceArea#1  485:1
 
  new customer 491: Fr
 491: [491: Fr]
     Customer# 485: Ju leaves and his total wait time is 6
 492: []
    ServiceArea#1  491:4
 493: []
    ServiceArea#1  491:3
 494: []
    ServiceArea#1  491:2
 495: []
    ServiceArea#1  491:1
 
  new customer 496: Se
 496: [496: Se]
     Customer# 491: Fr leaves and his total wait time is 5
 497: []
    ServiceArea#1  496:2
 498: []
    ServiceArea#1  496:1
 499: []
     Customer# 496: Se leaves and his total wait time is 3
 500: []
    ServiceArea#1  -1:-1
 501: []
    ServiceArea#1  -1:-1
 502: []
    ServiceArea#1  -1:-1
 
  new customer 503: So
 503: []
    ServiceArea#1  503:2
 504: []
    ServiceArea#1  503:1
 505: []
     Customer# 503: So leaves and his total wait time is 2
 506: []
    ServiceArea#1  -1:-1
 507: []
    ServiceArea#1  -1:-1
 508: []
    ServiceArea#1  -1:-1
 509: []
    ServiceArea#1  -1:-1
 
  new customer 510: Se
 510: []
    ServiceArea#1  510:3
 
  new customer 511: So
 511: [511: So]
    ServiceArea#1  510:2
 512: [511: So]
    ServiceArea#1  510:1
 513: [511: So]
     Customer# 510: Se leaves and his total wait time is 3
 514: []
    ServiceArea#1  511:4
 515: []
    ServiceArea#1  511:3
 516: []
    ServiceArea#1  511:2
 
  new customer 517: Se
 517: [517: Se]
    ServiceArea#1  511:1
 518: [517: Se]
     Customer# 511: So leaves and his total wait time is 7
 519: []
    ServiceArea#1  517:4
 520: []
    ServiceArea#1  517:3
 521: []
    ServiceArea#1  517:2
 
  new customer 522: Ju
 522: [522: Ju]
    ServiceArea#1  517:1
 
  new customer 523: Se
 523: [523: Se, 522: Ju]
     Customer# 517: Se leaves and his total wait time is 6
 
  new customer 524: So
 524: [522: Ju, 524: So]
    ServiceArea#1  523:1
 525: [522: Ju, 524: So]
     Customer# 523: Se leaves and his total wait time is 2
 526: [524: So]
    ServiceArea#1  522:6
 
  new customer 527: Se
 527: [527: Se, 524: So]
    ServiceArea#1  522:5
 528: [527: Se, 524: So]
    ServiceArea#1  522:4
 529: [527: Se, 524: So]
    ServiceArea#1  522:3
 530: [527: Se, 524: So]
    ServiceArea#1  522:2
 
  new customer 531: Ju
 531: [527: Se, 524: So, 531: Ju]
    ServiceArea#1  522:1
 532: [527: Se, 524: So, 531: Ju]
     Customer# 522: Ju leaves and his total wait time is 10
 533: [531: Ju, 524: So]
    ServiceArea#1  527:3
 
  new customer 534: So
 534: [531: Ju, 524: So, 534: So]
    ServiceArea#1  527:2
 535: [531: Ju, 524: So, 534: So]
    ServiceArea#1  527:1
 536: [531: Ju, 524: So, 534: So]
     Customer# 527: Se leaves and his total wait time is 9
 
  new customer 537: Fr
 537: [524: So, 534: So, 537: Fr]
    ServiceArea#1  531:3
 538: [524: So, 534: So, 537: Fr]
    ServiceArea#1  531:2
 539: [524: So, 534: So, 537: Fr]
    ServiceArea#1  531:1
 540: [524: So, 534: So, 537: Fr]
     Customer# 531: Ju leaves and his total wait time is 9
 541: [534: So, 537: Fr]
    ServiceArea#1  524:5
 542: [534: So, 537: Fr]
    ServiceArea#1  524:4
 
  new customer 543: Fr
 543: [534: So, 537: Fr, 543: Fr]
    ServiceArea#1  524:3
 544: [534: So, 537: Fr, 543: Fr]
    ServiceArea#1  524:2
 545: [534: So, 537: Fr, 543: Fr]
    ServiceArea#1  524:1
 546: [534: So, 537: Fr, 543: Fr]
     Customer# 524: So leaves and his total wait time is 22
 547: [537: Fr, 543: Fr]
    ServiceArea#1  534:4
 548: [537: Fr, 543: Fr]
    ServiceArea#1  534:3
 549: [537: Fr, 543: Fr]
    ServiceArea#1  534:2
 550: [537: Fr, 543: Fr]
    ServiceArea#1  534:1
 551: [537: Fr, 543: Fr]
     Customer# 534: So leaves and his total wait time is 17
 552: [543: Fr]
    ServiceArea#1  537:3
 553: [543: Fr]
    ServiceArea#1  537:2
 
  new customer 554: Ju
 554: [554: Ju, 543: Fr]
    ServiceArea#1  537:1
 555: [554: Ju, 543: Fr]
     Customer# 537: Fr leaves and his total wait time is 18
 556: [543: Fr]
    ServiceArea#1  554:4
 557: [543: Fr]
    ServiceArea#1  554:3
 
  new customer 558: So
 558: [558: So, 543: Fr]
    ServiceArea#1  554:2
 559: [558: So, 543: Fr]
    ServiceArea#1  554:1
 560: [558: So, 543: Fr]
     Customer# 554: Ju leaves and his total wait time is 6
 
  new customer 561: Fr
 561: [543: Fr, 561: Fr]
    ServiceArea#1  558:5
 562: [543: Fr, 561: Fr]
    ServiceArea#1  558:4
 
  new customer 563: Se
 563: [563: Se, 561: Fr, 543: Fr]
    ServiceArea#1  558:3
 564: [563: Se, 561: Fr, 543: Fr]
    ServiceArea#1  558:2
 
  new customer 565: Fr
 565: [563: Se, 561: Fr, 543: Fr, 565: Fr]
    ServiceArea#1  558:1
 566: [563: Se, 561: Fr, 543: Fr, 565: Fr]
     Customer# 558: So leaves and his total wait time is 8
 
  new customer 567: Ju
 567: [567: Ju, 543: Fr, 565: Fr, 561: Fr]
    ServiceArea#1  563:5
 568: [567: Ju, 543: Fr, 565: Fr, 561: Fr]
    ServiceArea#1  563:4
 569: [567: Ju, 543: Fr, 565: Fr, 561: Fr]
    ServiceArea#1  563:3
 570: [567: Ju, 543: Fr, 565: Fr, 561: Fr]
    ServiceArea#1  563:2
 
  new customer 571: So
 571: [567: Ju, 571: So, 565: Fr, 561: Fr, 543: Fr]
    ServiceArea#1  563:1
 572: [567: Ju, 571: So, 565: Fr, 561: Fr, 543: Fr]
     Customer# 563: Se leaves and his total wait time is 9
 573: [571: So, 543: Fr, 565: Fr, 561: Fr]
    ServiceArea#1  567:4
 574: [571: So, 543: Fr, 565: Fr, 561: Fr]
    ServiceArea#1  567:3
 575: [571: So, 543: Fr, 565: Fr, 561: Fr]
    ServiceArea#1  567:2
 576: [571: So, 543: Fr, 565: Fr, 561: Fr]
    ServiceArea#1  567:1
 577: [571: So, 543: Fr, 565: Fr, 561: Fr]
     Customer# 567: Ju leaves and his total wait time is 10
 578: [543: Fr, 561: Fr, 565: Fr]
    ServiceArea#1  571:1
 579: [543: Fr, 561: Fr, 565: Fr]
     Customer# 571: So leaves and his total wait time is 8
 
  new customer 580: Fr
 580: [561: Fr, 565: Fr, 580: Fr]
    ServiceArea#1  543:6
 581: [561: Fr, 565: Fr, 580: Fr]
    ServiceArea#1  543:5
 582: [561: Fr, 565: Fr, 580: Fr]
    ServiceArea#1  543:4
 
  new customer 583: Fr
 583: [561: Fr, 565: Fr, 580: Fr, 583: Fr]
    ServiceArea#1  543:3
 
  new customer 584: Fr
 584: [561: Fr, 565: Fr, 580: Fr, 583: Fr, 584: Fr]
    ServiceArea#1  543:2
 
  new customer 585: Ju
 585: [585: Ju, 565: Fr, 561: Fr, 583: Fr, 584: Fr, 580: Fr]
    ServiceArea#1  543:1
 
  new customer 586: Se
 586: [586: Se, 565: Fr, 585: Ju, 583: Fr, 584: Fr, 580: Fr, 561: Fr]
     Customer# 543: Fr leaves and his total wait time is 43
 587: [585: Ju, 565: Fr, 561: Fr, 583: Fr, 584: Fr, 580: Fr]
    ServiceArea#1  586:3
 
  new customer 588: Ju
 588: [585: Ju, 565: Fr, 588: Ju, 583: Fr, 584: Fr, 580: Fr, 561: Fr]
    ServiceArea#1  586:2
 
  new customer 589: Ju
 589: [585: Ju, 589: Ju, 588: Ju, 565: Fr, 584: Fr, 580: Fr, 561: Fr, 583: Fr]
    ServiceArea#1  586:1
 590: [585: Ju, 589: Ju, 588: Ju, 565: Fr, 584: Fr, 580: Fr, 561: Fr, 583: Fr]
     Customer# 586: Se leaves and his total wait time is 4
 591: [588: Ju, 589: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr]
    ServiceArea#1  585:3
 
  new customer 592: Ju
 592: [588: Ju, 589: Ju, 561: Fr, 592: Ju, 584: Fr, 580: Fr, 583: Fr, 565: Fr]
    ServiceArea#1  585:2
 593: [588: Ju, 589: Ju, 561: Fr, 592: Ju, 584: Fr, 580: Fr, 583: Fr, 565: Fr]
    ServiceArea#1  585:1
 594: [588: Ju, 589: Ju, 561: Fr, 592: Ju, 584: Fr, 580: Fr, 583: Fr, 565: Fr]
     Customer# 585: Ju leaves and his total wait time is 9
 595: [589: Ju, 592: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr]
    ServiceArea#1  588:3
 596: [589: Ju, 592: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr]
    ServiceArea#1  588:2
 
  new customer 597: Fr
 597: [589: Ju, 592: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr]
    ServiceArea#1  588:1
 598: [589: Ju, 592: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr]
     Customer# 588: Ju leaves and his total wait time is 10
 599: [592: Ju, 565: Fr, 561: Fr, 597: Fr, 584: Fr, 580: Fr, 583: Fr]
    ServiceArea#1  589:5
 600: [592: Ju, 565: Fr, 561: Fr, 597: Fr, 584: Fr, 580: Fr, 583: Fr]
    ServiceArea#1  589:4
 
  new customer 601: Se
 601: [601: Se, 592: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr]
    ServiceArea#1  589:3
 
  new customer 602: Se
 602: [601: Se, 602: Se, 561: Fr, 592: Ju, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 565: Fr]
    ServiceArea#1  589:2
 603: [601: Se, 602: Se, 561: Fr, 592: Ju, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 565: Fr]
    ServiceArea#1  589:1
 604: [601: Se, 602: Se, 561: Fr, 592: Ju, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 565: Fr]
     Customer# 589: Ju leaves and his total wait time is 15
 605: [602: Se, 592: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr]
    ServiceArea#1  601:3
 
  new customer 606: Ju
 606: [602: Se, 592: Ju, 561: Fr, 606: Ju, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 565: Fr]
    ServiceArea#1  601:2
 607: [602: Se, 592: Ju, 561: Fr, 606: Ju, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 565: Fr]
    ServiceArea#1  601:1
 608: [602: Se, 592: Ju, 561: Fr, 606: Ju, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 565: Fr]
     Customer# 601: Se leaves and his total wait time is 7
 609: [592: Ju, 606: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr]
    ServiceArea#1  602:3
 610: [592: Ju, 606: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr]
    ServiceArea#1  602:2
 611: [592: Ju, 606: Ju, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr]
    ServiceArea#1  602:1
 
  new customer 612: So
 612: [592: Ju, 606: Ju, 561: Fr, 612: So, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 565: Fr]
     Customer# 602: Se leaves and his total wait time is 10
 613: [606: Ju, 612: So, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr]
    ServiceArea#1  592:2
 
  new customer 614: So
 614: [606: Ju, 612: So, 561: Fr, 614: So, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 565: Fr]
    ServiceArea#1  592:1
 615: [606: Ju, 612: So, 561: Fr, 614: So, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 565: Fr]
     Customer# 592: Ju leaves and his total wait time is 23
 
  new customer 616: Fr
 616: [612: So, 614: So, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 616: Fr]
    ServiceArea#1  606:4
 617: [612: So, 614: So, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 616: Fr]
    ServiceArea#1  606:3
 618: [612: So, 614: So, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 616: Fr]
    ServiceArea#1  606:2
 619: [612: So, 614: So, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 616: Fr]
    ServiceArea#1  606:1
 620: [612: So, 614: So, 561: Fr, 565: Fr, 584: Fr, 580: Fr, 583: Fr, 597: Fr, 616: Fr]
     Customer# 606: Ju leaves and his total wait time is 14
 621: [614: So, 565: Fr, 561: Fr, 597: Fr, 584: Fr, 580: Fr, 583: Fr, 616: Fr]
    ServiceArea#1  612:2
 622: [614: So, 565: Fr, 561: Fr, 597: Fr, 584: Fr, 580: Fr, 583: Fr, 616: Fr]
    ServiceArea#1  612:1
 623: [614: So, 565: Fr, 561: Fr, 597: Fr, 584: Fr, 580: Fr, 583: Fr, 616: Fr]
     Customer# 612: So leaves and his total wait time is 11
 624: [561: Fr, 565: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr, 583: Fr]
    ServiceArea#1  614:2
 625: [561: Fr, 565: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr, 583: Fr]
    ServiceArea#1  614:1
 626: [561: Fr, 565: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr, 583: Fr]
     Customer# 614: So leaves and his total wait time is 12
 627: [565: Fr, 583: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr]
    ServiceArea#1  561:4
 628: [565: Fr, 583: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr]
    ServiceArea#1  561:3
 629: [565: Fr, 583: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr]
    ServiceArea#1  561:2
 630: [565: Fr, 583: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr]
    ServiceArea#1  561:1
 631: [565: Fr, 583: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr]
     Customer# 561: Fr leaves and his total wait time is 70
 
  new customer 632: Se
 632: [632: Se, 583: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr]
    ServiceArea#1  565:2
 633: [632: Se, 583: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr]
    ServiceArea#1  565:1
 634: [632: Se, 583: Fr, 580: Fr, 597: Fr, 584: Fr, 616: Fr]
     Customer# 565: Fr leaves and his total wait time is 69
 635: [580: Fr, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  632:6
 636: [580: Fr, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  632:5
 637: [580: Fr, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  632:4
 638: [580: Fr, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  632:3
 639: [580: Fr, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  632:2
 640: [580: Fr, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  632:1
 641: [580: Fr, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
     Customer# 632: Se leaves and his total wait time is 9
 642: [583: Fr, 584: Fr, 616: Fr, 597: Fr]
    ServiceArea#1  580:6
 
  new customer 643: Ju
 643: [643: Ju, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  580:5
 644: [643: Ju, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  580:4
 645: [643: Ju, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  580:3
 646: [643: Ju, 583: Fr, 616: Fr, 597: Fr, 584: Fr]
    ServiceArea#1  580:2
 
  new customer 647: Fr
 647: [643: Ju, 583: Fr, 616: Fr, 597: Fr, 584: Fr, 647: Fr]
    ServiceArea#1  580:1
 648: [643: Ju, 583: Fr, 616: Fr, 597: Fr, 584: Fr, 647: Fr]
     Customer# 580: Fr leaves and his total wait time is 68
 
  new customer 649: Se
 649: [649: Se, 584: Fr, 583: Fr, 597: Fr, 647: Fr, 616: Fr]
    ServiceArea#1  643:2
 650: [649: Se, 584: Fr, 583: Fr, 597: Fr, 647: Fr, 616: Fr]
    ServiceArea#1  643:1
 651: [649: Se, 584: Fr, 583: Fr, 597: Fr, 647: Fr, 616: Fr]
     Customer# 643: Ju leaves and his total wait time is 8
 
  new customer 652: Fr
 652: [583: Fr, 584: Fr, 616: Fr, 597: Fr, 647: Fr, 652: Fr]
    ServiceArea#1  649:4
 653: [583: Fr, 584: Fr, 616: Fr, 597: Fr, 647: Fr, 652: Fr]
    ServiceArea#1  649:3
 
  new customer 654: Fr
 654: [583: Fr, 584: Fr, 616: Fr, 597: Fr, 647: Fr, 652: Fr, 654: Fr]
    ServiceArea#1  649:2
 655: [583: Fr, 584: Fr, 616: Fr, 597: Fr, 647: Fr, 652: Fr, 654: Fr]
    ServiceArea#1  649:1
 
  new customer 656: Fr
 656: [583: Fr, 584: Fr, 616: Fr, 597: Fr, 647: Fr, 652: Fr, 654: Fr, 656: Fr]
     Customer# 649: Se leaves and his total wait time is 7
 657: [584: Fr, 597: Fr, 616: Fr, 656: Fr, 647: Fr, 652: Fr, 654: Fr]
    ServiceArea#1  583:1
 658: [584: Fr, 597: Fr, 616: Fr, 656: Fr, 647: Fr, 652: Fr, 654: Fr]
     Customer# 583: Fr leaves and his total wait time is 75
 
  new customer 659: Se
 659: [659: Se, 647: Fr, 597: Fr, 656: Fr, 654: Fr, 652: Fr, 616: Fr]
    ServiceArea#1  584:2
 
  new customer 660: Fr
 660: [659: Se, 647: Fr, 597: Fr, 656: Fr, 654: Fr, 652: Fr, 616: Fr, 660: Fr]
    ServiceArea#1  584:1
 661: [659: Se, 647: Fr, 597: Fr, 656: Fr, 654: Fr, 652: Fr, 616: Fr, 660: Fr]
     Customer# 584: Fr leaves and his total wait time is 77
 662: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr]
    ServiceArea#1  659:5
 663: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr]
    ServiceArea#1  659:4
 
  new customer 664: So
 664: [664: So, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 656: Fr]
    ServiceArea#1  659:3
 665: [664: So, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 656: Fr]
    ServiceArea#1  659:2
 
  new customer 666: Se
 666: [666: Se, 664: So, 616: Fr, 597: Fr, 654: Fr, 652: Fr, 660: Fr, 656: Fr, 647: Fr]
    ServiceArea#1  659:1
 667: [666: Se, 664: So, 616: Fr, 597: Fr, 654: Fr, 652: Fr, 660: Fr, 656: Fr, 647: Fr]
     Customer# 659: Se leaves and his total wait time is 8
 668: [664: So, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 656: Fr]
    ServiceArea#1  666:3
 669: [664: So, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 656: Fr]
    ServiceArea#1  666:2
 670: [664: So, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 656: Fr]
    ServiceArea#1  666:1
 
  new customer 671: Fr
 671: [664: So, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 656: Fr, 671: Fr]
     Customer# 666: Se leaves and his total wait time is 5
 672: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  664:4
 673: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  664:3
 674: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  664:2
 675: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  664:1
 
  new customer 676: Ju
 676: [676: Ju, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 656: Fr]
     Customer# 664: So leaves and his total wait time is 12
 677: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  676:6
 678: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  676:5
 679: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  676:4
 680: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  676:3
 681: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  676:2
 682: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  676:1
 
  new customer 683: Ju
 683: [683: Ju, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 656: Fr]
     Customer# 676: Ju leaves and his total wait time is 7
 684: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  683:3
 685: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  683:2
 686: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  683:1
 
  new customer 687: Se
 687: [687: Se, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 656: Fr]
     Customer# 683: Ju leaves and his total wait time is 4
 
  new customer 688: Ju
 688: [688: Ju, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 656: Fr]
    ServiceArea#1  687:3
 689: [688: Ju, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 656: Fr]
    ServiceArea#1  687:2
 690: [688: Ju, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 656: Fr]
    ServiceArea#1  687:1
 691: [688: Ju, 597: Fr, 616: Fr, 647: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 656: Fr]
     Customer# 687: Se leaves and his total wait time is 4
 
  new customer 692: Fr
 692: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  688:2
 693: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  688:1
 694: [597: Fr, 647: Fr, 616: Fr, 656: Fr, 654: Fr, 652: Fr, 660: Fr, 671: Fr, 692: Fr]
     Customer# 688: Ju leaves and his total wait time is 6
 
  new customer 695: Se
 695: [695: Se, 616: Fr, 652: Fr, 647: Fr, 654: Fr, 692: Fr, 660: Fr, 671: Fr, 656: Fr]
    ServiceArea#1  597:3
 696: [695: Se, 616: Fr, 652: Fr, 647: Fr, 654: Fr, 692: Fr, 660: Fr, 671: Fr, 656: Fr]
    ServiceArea#1  597:2
 697: [695: Se, 616: Fr, 652: Fr, 647: Fr, 654: Fr, 692: Fr, 660: Fr, 671: Fr, 656: Fr]
    ServiceArea#1  597:1
 698: [695: Se, 616: Fr, 652: Fr, 647: Fr, 654: Fr, 692: Fr, 660: Fr, 671: Fr, 656: Fr]
     Customer# 597: Fr leaves and his total wait time is 101
 699: [616: Fr, 647: Fr, 652: Fr, 656: Fr, 654: Fr, 692: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  695:2
 700: [616: Fr, 647: Fr, 652: Fr, 656: Fr, 654: Fr, 692: Fr, 660: Fr, 671: Fr]
    ServiceArea#1  695:1
 701: [616: Fr, 647: Fr, 652: Fr, 656: Fr, 654: Fr, 692: Fr, 660: Fr, 671: Fr]
     Customer# 695: Se leaves and his total wait time is 6
 702: [647: Fr, 654: Fr, 652: Fr, 656: Fr, 671: Fr, 692: Fr, 660: Fr]
    ServiceArea#1  616:5
 703: [647: Fr, 654: Fr, 652: Fr, 656: Fr, 671: Fr, 692: Fr, 660: Fr]
    ServiceArea#1  616:4
 
  new customer 704: Ju
 704: [704: Ju, 647: Fr, 652: Fr, 654: Fr, 671: Fr, 692: Fr, 660: Fr, 656: Fr]
    ServiceArea#1  616:3
 705: [704: Ju, 647: Fr, 652: Fr, 654: Fr, 671: Fr, 692: Fr, 660: Fr, 656: Fr]
    ServiceArea#1  616:2
 706: [704: Ju, 647: Fr, 652: Fr, 654: Fr, 671: Fr, 692: Fr, 660: Fr, 656: Fr]
    ServiceArea#1  616:1
 707: [704: Ju, 647: Fr, 652: Fr, 654: Fr, 671: Fr, 692: Fr, 660: Fr, 656: Fr]
     Customer# 616: Fr leaves and his total wait time is 91
 708: [647: Fr, 654: Fr, 652: Fr, 656: Fr, 671: Fr, 692: Fr, 660: Fr]
    ServiceArea#1  704:5
 709: [647: Fr, 654: Fr, 652: Fr, 656: Fr, 671: Fr, 692: Fr, 660: Fr]
    ServiceArea#1  704:4
 710: [647: Fr, 654: Fr, 652: Fr, 656: Fr, 671: Fr, 692: Fr, 660: Fr]
    ServiceArea#1  704:3
 711: [647: Fr, 654: Fr, 652: Fr, 656: Fr, 671: Fr, 692: Fr, 660: Fr]
    ServiceArea#1  704:2
 712: [647: Fr, 654: Fr, 652: Fr, 656: Fr, 671: Fr, 692: Fr, 660: Fr]
    ServiceArea#1  704:1
 713: [647: Fr, 654: Fr, 652: Fr, 656: Fr, 671: Fr, 692: Fr, 660: Fr]
     Customer# 704: Ju leaves and his total wait time is 9
 714: [652: Fr, 654: Fr, 660: Fr, 656: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  647:1
 715: [652: Fr, 654: Fr, 660: Fr, 656: Fr, 671: Fr, 692: Fr]
     Customer# 647: Fr leaves and his total wait time is 68
 
  new customer 716: So
 716: [716: So, 656: Fr, 654: Fr, 692: Fr, 671: Fr, 660: Fr]
    ServiceArea#1  652:1
 717: [716: So, 656: Fr, 654: Fr, 692: Fr, 671: Fr, 660: Fr]
     Customer# 652: Fr leaves and his total wait time is 65
 718: [654: Fr, 656: Fr, 660: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  716:4
 719: [654: Fr, 656: Fr, 660: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  716:3
 720: [654: Fr, 656: Fr, 660: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  716:2
 721: [654: Fr, 656: Fr, 660: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  716:1
 722: [654: Fr, 656: Fr, 660: Fr, 692: Fr, 671: Fr]
     Customer# 716: So leaves and his total wait time is 6
 723: [656: Fr, 671: Fr, 660: Fr, 692: Fr]
    ServiceArea#1  654:4
 724: [656: Fr, 671: Fr, 660: Fr, 692: Fr]
    ServiceArea#1  654:3
 725: [656: Fr, 671: Fr, 660: Fr, 692: Fr]
    ServiceArea#1  654:2
 726: [656: Fr, 671: Fr, 660: Fr, 692: Fr]
    ServiceArea#1  654:1
 727: [656: Fr, 671: Fr, 660: Fr, 692: Fr]
     Customer# 654: Fr leaves and his total wait time is 73
 
  new customer 728: Fr
 728: [660: Fr, 671: Fr, 692: Fr, 728: Fr]
    ServiceArea#1  656:6
 
  new customer 729: Se
 729: [729: Se, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  656:5
 730: [729: Se, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  656:4
 731: [729: Se, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  656:3
 732: [729: Se, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  656:2
 
  new customer 733: Ju
 733: [729: Se, 660: Fr, 733: Ju, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  656:1
 734: [729: Se, 660: Fr, 733: Ju, 728: Fr, 671: Fr, 692: Fr]
     Customer# 656: Fr leaves and his total wait time is 78
 
  new customer 735: So
 735: [733: Ju, 660: Fr, 735: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  729:6
 736: [733: Ju, 660: Fr, 735: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  729:5
 737: [733: Ju, 660: Fr, 735: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  729:4
 738: [733: Ju, 660: Fr, 735: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  729:3
 739: [733: Ju, 660: Fr, 735: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  729:2
 740: [733: Ju, 660: Fr, 735: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  729:1
 741: [733: Ju, 660: Fr, 735: So, 728: Fr, 671: Fr, 692: Fr]
     Customer# 729: Se leaves and his total wait time is 12
 742: [735: So, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  733:6
 743: [735: So, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  733:5
 
  new customer 744: So
 744: [735: So, 660: Fr, 744: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  733:4
 745: [735: So, 660: Fr, 744: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  733:3
 746: [735: So, 660: Fr, 744: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  733:2
 747: [735: So, 660: Fr, 744: So, 728: Fr, 671: Fr, 692: Fr]
    ServiceArea#1  733:1
 748: [735: So, 660: Fr, 744: So, 728: Fr, 671: Fr, 692: Fr]
     Customer# 733: Ju leaves and his total wait time is 15
 749: [744: So, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  735:5
 750: [744: So, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  735:4
 751: [744: So, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  735:3
 752: [744: So, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  735:2
 753: [744: So, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  735:1
 754: [744: So, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
     Customer# 735: So leaves and his total wait time is 19
 755: [660: Fr, 671: Fr, 692: Fr, 728: Fr]
    ServiceArea#1  744:5
 
  new customer 756: Ju
 756: [756: Ju, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  744:4
 757: [756: Ju, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  744:3
 758: [756: Ju, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  744:2
 759: [756: Ju, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
    ServiceArea#1  744:1
 760: [756: Ju, 660: Fr, 692: Fr, 728: Fr, 671: Fr]
     Customer# 744: So leaves and his total wait time is 16
 761: [660: Fr, 671: Fr, 692: Fr, 728: Fr]
    ServiceArea#1  756:2
 762: [660: Fr, 671: Fr, 692: Fr, 728: Fr]
    ServiceArea#1  756:1
 
  new customer 763: Fr
 763: [660: Fr, 671: Fr, 692: Fr, 728: Fr, 763: Fr]
     Customer# 756: Ju leaves and his total wait time is 7
 764: [671: Fr, 728: Fr, 692: Fr, 763: Fr]
    ServiceArea#1  660:4
 765: [671: Fr, 728: Fr, 692: Fr, 763: Fr]
    ServiceArea#1  660:3
 766: [671: Fr, 728: Fr, 692: Fr, 763: Fr]
    ServiceArea#1  660:2
 
  new customer 767: Se
 767: [767: Se, 671: Fr, 692: Fr, 763: Fr, 728: Fr]
    ServiceArea#1  660:1
 
  new customer 768: Fr
 768: [767: Se, 671: Fr, 692: Fr, 763: Fr, 728: Fr, 768: Fr]
     Customer# 660: Fr leaves and his total wait time is 108
 
  new customer 769: So
 769: [769: So, 728: Fr, 671: Fr, 763: Fr, 768: Fr, 692: Fr]
    ServiceArea#1  767:5
 770: [769: So, 728: Fr, 671: Fr, 763: Fr, 768: Fr, 692: Fr]
    ServiceArea#1  767:4
 
  new customer 771: Se
 771: [771: Se, 728: Fr, 769: So, 763: Fr, 768: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  767:3
 772: [771: Se, 728: Fr, 769: So, 763: Fr, 768: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  767:2
 773: [771: Se, 728: Fr, 769: So, 763: Fr, 768: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  767:1
 774: [771: Se, 728: Fr, 769: So, 763: Fr, 768: Fr, 692: Fr, 671: Fr]
     Customer# 767: Se leaves and his total wait time is 7
 775: [769: So, 728: Fr, 671: Fr, 763: Fr, 768: Fr, 692: Fr]
    ServiceArea#1  771:5
 776: [769: So, 728: Fr, 671: Fr, 763: Fr, 768: Fr, 692: Fr]
    ServiceArea#1  771:4
 
  new customer 777: Ju
 777: [777: Ju, 728: Fr, 769: So, 763: Fr, 768: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  771:3
 778: [777: Ju, 728: Fr, 769: So, 763: Fr, 768: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  771:2
 779: [777: Ju, 728: Fr, 769: So, 763: Fr, 768: Fr, 692: Fr, 671: Fr]
    ServiceArea#1  771:1
 780: [777: Ju, 728: Fr, 769: So, 763: Fr, 768: Fr, 692: Fr, 671: Fr]
     Customer# 771: Se leaves and his total wait time is 9
 781: [769: So, 728: Fr, 671: Fr, 763: Fr, 768: Fr, 692: Fr]
    ServiceArea#1  777:3
 782: [769: So, 728: Fr, 671: Fr, 763: Fr, 768: Fr, 692: Fr]
    ServiceArea#1  777:2
 783: [769: So, 728: Fr, 671: Fr, 763: Fr, 768: Fr, 692: Fr]
    ServiceArea#1  777:1
 784: [769: So, 728: Fr, 671: Fr, 763: Fr, 768: Fr, 692: Fr]
     Customer# 777: Ju leaves and his total wait time is 7
 785: [671: Fr, 728: Fr, 692: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  769:5
 786: [671: Fr, 728: Fr, 692: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  769:4
 787: [671: Fr, 728: Fr, 692: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  769:3
 788: [671: Fr, 728: Fr, 692: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  769:2
 789: [671: Fr, 728: Fr, 692: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  769:1
 790: [671: Fr, 728: Fr, 692: Fr, 763: Fr, 768: Fr]
     Customer# 769: So leaves and his total wait time is 21
 791: [692: Fr, 728: Fr, 768: Fr, 763: Fr]
    ServiceArea#1  671:5
 
  new customer 792: Se
 792: [792: Se, 692: Fr, 768: Fr, 763: Fr, 728: Fr]
    ServiceArea#1  671:4
 793: [792: Se, 692: Fr, 768: Fr, 763: Fr, 728: Fr]
    ServiceArea#1  671:3
 794: [792: Se, 692: Fr, 768: Fr, 763: Fr, 728: Fr]
    ServiceArea#1  671:2
 
  new customer 795: Ju
 795: [792: Se, 692: Fr, 795: Ju, 763: Fr, 728: Fr, 768: Fr]
    ServiceArea#1  671:1
 796: [792: Se, 692: Fr, 795: Ju, 763: Fr, 728: Fr, 768: Fr]
     Customer# 671: Fr leaves and his total wait time is 125
 797: [795: Ju, 692: Fr, 768: Fr, 763: Fr, 728: Fr]
    ServiceArea#1  792:4
 798: [795: Ju, 692: Fr, 768: Fr, 763: Fr, 728: Fr]
    ServiceArea#1  792:3
 799: [795: Ju, 692: Fr, 768: Fr, 763: Fr, 728: Fr]
    ServiceArea#1  792:2
 800: [795: Ju, 692: Fr, 768: Fr, 763: Fr, 728: Fr]
    ServiceArea#1  792:1
 801: [795: Ju, 692: Fr, 768: Fr, 763: Fr, 728: Fr]
     Customer# 792: Se leaves and his total wait time is 9
 802: [692: Fr, 728: Fr, 768: Fr, 763: Fr]
    ServiceArea#1  795:2
 803: [692: Fr, 728: Fr, 768: Fr, 763: Fr]
    ServiceArea#1  795:1
 804: [692: Fr, 728: Fr, 768: Fr, 763: Fr]
     Customer# 795: Ju leaves and his total wait time is 9
 805: [728: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  692:4
 806: [728: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  692:3
 807: [728: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  692:2
 
  new customer 808: Se
 808: [808: Se, 728: Fr, 768: Fr, 763: Fr]
    ServiceArea#1  692:1
 809: [808: Se, 728: Fr, 768: Fr, 763: Fr]
     Customer# 692: Fr leaves and his total wait time is 117
 810: [728: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  808:5
 811: [728: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  808:4
 812: [728: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  808:3
 813: [728: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  808:2
 814: [728: Fr, 763: Fr, 768: Fr]
    ServiceArea#1  808:1
 815: [728: Fr, 763: Fr, 768: Fr]
     Customer# 808: Se leaves and his total wait time is 7
 816: [763: Fr, 768: Fr]
    ServiceArea#1  728:2
 817: [763: Fr, 768: Fr]
    ServiceArea#1  728:1
 818: [763: Fr, 768: Fr]
     Customer# 728: Fr leaves and his total wait time is 90
 819: [768: Fr]
    ServiceArea#1  763:5
 820: [768: Fr]
    ServiceArea#1  763:4
 821: [768: Fr]
    ServiceArea#1  763:3
 822: [768: Fr]
    ServiceArea#1  763:2
 823: [768: Fr]
    ServiceArea#1  763:1
 824: [768: Fr]
     Customer# 763: Fr leaves and his total wait time is 61
 825: []
    ServiceArea#1  768:2
 826: []
    ServiceArea#1  768:1
 827: []
     Customer# 768: Fr leaves and his total wait time is 59
 828: []
    ServiceArea#1  -1:-1
 829: []
    ServiceArea#1  -1:-1
 830: []
    ServiceArea#1  -1:-1
 
  new customer 831: Fr
 831: []
    ServiceArea#1  831:4
 832: []
    ServiceArea#1  831:3
 833: []
    ServiceArea#1  831:2
 
  new customer 834: Fr
 834: [834: Fr]
    ServiceArea#1  831:1
 835: [834: Fr]
     Customer# 831: Fr leaves and his total wait time is 4
 836: []
    ServiceArea#1  834:3
 837: []
    ServiceArea#1  834:2
 838: []
    ServiceArea#1  834:1
 839: []
     Customer# 834: Fr leaves and his total wait time is 5
 840: []
    ServiceArea#1  -1:-1
 
  new customer 841: So
 841: []
    ServiceArea#1  841:4
 842: []
    ServiceArea#1  841:3
 
  new customer 843: Fr
 843: [843: Fr]
    ServiceArea#1  841:2
 844: [843: Fr]
    ServiceArea#1  841:1
 845: [843: Fr]
     Customer# 841: So leaves and his total wait time is 4
 846: []
    ServiceArea#1  843:5
 847: []
    ServiceArea#1  843:4
 
  new customer 848: Se
 848: [848: Se]
    ServiceArea#1  843:3
 
  new customer 849: Se
 849: [848: Se, 849: Se]
    ServiceArea#1  843:2
 850: [848: Se, 849: Se]
    ServiceArea#1  843:1
 
  new customer 851: So
 851: [848: Se, 849: Se, 851: So]
     Customer# 843: Fr leaves and his total wait time is 8
 
  new customer 852: Fr
 852: [849: Se, 851: So, 852: Fr]
    ServiceArea#1  848:5
 853: [849: Se, 851: So, 852: Fr]
    ServiceArea#1  848:4
 854: [849: Se, 851: So, 852: Fr]
    ServiceArea#1  848:3
 855: [849: Se, 851: So, 852: Fr]
    ServiceArea#1  848:2
 856: [849: Se, 851: So, 852: Fr]
    ServiceArea#1  848:1
 857: [849: Se, 851: So, 852: Fr]
     Customer# 848: Se leaves and his total wait time is 9
 
  new customer 858: Ju
 858: [858: Ju, 852: Fr, 851: So]
    ServiceArea#1  849:1
 
  new customer 859: So
 859: [858: Ju, 859: So, 851: So, 852: Fr]
     Customer# 849: Se leaves and his total wait time is 10
 
  new customer 860: Ju
 860: [860: Ju, 851: So, 852: Fr, 859: So]
    ServiceArea#1  858:2
 861: [860: Ju, 851: So, 852: Fr, 859: So]
    ServiceArea#1  858:1
 862: [860: Ju, 851: So, 852: Fr, 859: So]
     Customer# 858: Ju leaves and his total wait time is 4
 863: [851: So, 859: So, 852: Fr]
    ServiceArea#1  860:2
 864: [851: So, 859: So, 852: Fr]
    ServiceArea#1  860:1
 865: [851: So, 859: So, 852: Fr]
     Customer# 860: Ju leaves and his total wait time is 5
 866: [859: So, 852: Fr]
    ServiceArea#1  851:1
 867: [859: So, 852: Fr]
     Customer# 851: So leaves and his total wait time is 16
 868: [852: Fr]
    ServiceArea#1  859:2
 869: [852: Fr]
    ServiceArea#1  859:1
 870: [852: Fr]
     Customer# 859: So leaves and his total wait time is 11
 871: []
    ServiceArea#1  852:1
 872: []
     Customer# 852: Fr leaves and his total wait time is 20
 
  new customer 873: So
 873: []
    ServiceArea#1  873:6
 874: []
    ServiceArea#1  873:5
 875: []
    ServiceArea#1  873:4
 876: []
    ServiceArea#1  873:3
 877: []
    ServiceArea#1  873:2
 878: []
    ServiceArea#1  873:1
 879: []
     Customer# 873: So leaves and his total wait time is 6
 
  new customer 880: So
 880: []
    ServiceArea#1  880:6
 881: []
    ServiceArea#1  880:5
 882: []
    ServiceArea#1  880:4
 883: []
    ServiceArea#1  880:3
 884: []
    ServiceArea#1  880:2
 885: []
    ServiceArea#1  880:1
 
  new customer 886: Se
 886: [886: Se]
     Customer# 880: So leaves and his total wait time is 6
 887: []
    ServiceArea#1  886:3
 
  new customer 888: Se
 888: [888: Se]
    ServiceArea#1  886:2
 889: [888: Se]
    ServiceArea#1  886:1
 890: [888: Se]
     Customer# 886: Se leaves and his total wait time is 4
 891: []
    ServiceArea#1  888:3
 892: []
    ServiceArea#1  888:2
 893: []
    ServiceArea#1  888:1
 
  new customer 894: Ju
 894: [894: Ju]
     Customer# 888: Se leaves and his total wait time is 6
 895: []
    ServiceArea#1  894:5
 896: []
    ServiceArea#1  894:4
 
  new customer 897: Se
 897: [897: Se]
    ServiceArea#1  894:3
 898: [897: Se]
    ServiceArea#1  894:2
 899: [897: Se]
    ServiceArea#1  894:1
 
  new customer 900: So
 900: [897: Se, 900: So]
     Customer# 894: Ju leaves and his total wait time is 6
 901: [900: So]
    ServiceArea#1  897:4
 902: [900: So]
    ServiceArea#1  897:3
 903: [900: So]
    ServiceArea#1  897:2
 904: [900: So]
    ServiceArea#1  897:1
 905: [900: So]
     Customer# 897: Se leaves and his total wait time is 8
 906: []
    ServiceArea#1  900:1
 
  new customer 907: Fr
 907: [907: Fr]
     Customer# 900: So leaves and his total wait time is 7
 908: []
    ServiceArea#1  907:1
 909: []
     Customer# 907: Fr leaves and his total wait time is 2
 910: []
    ServiceArea#1  -1:-1
 911: []
    ServiceArea#1  -1:-1
 912: []
    ServiceArea#1  -1:-1
 
  new customer 913: So
 913: []
    ServiceArea#1  913:4
 914: []
    ServiceArea#1  913:3
 915: []
    ServiceArea#1  913:2
 
  new customer 916: Fr
 916: [916: Fr]
    ServiceArea#1  913:1
 917: [916: Fr]
     Customer# 913: So leaves and his total wait time is 4
 918: []
    ServiceArea#1  916:1
 919: []
     Customer# 916: Fr leaves and his total wait time is 3
 
  new customer 920: Ju
 920: []
    ServiceArea#1  920:5
 921: []
    ServiceArea#1  920:4
 922: []
    ServiceArea#1  920:3
 923: []
    ServiceArea#1  920:2
 924: []
    ServiceArea#1  920:1
 925: []
     Customer# 920: Ju leaves and his total wait time is 5
 926: []
    ServiceArea#1  -1:-1
 927: []
    ServiceArea#1  -1:-1
 
  new customer 928: Fr
 928: []
    ServiceArea#1  928:6
 
  new customer 929: So
 929: [929: So]
    ServiceArea#1  928:5
 930: [929: So]
    ServiceArea#1  928:4
 931: [929: So]
    ServiceArea#1  928:3
 932: [929: So]
    ServiceArea#1  928:2
 933: [929: So]
    ServiceArea#1  928:1
 934: [929: So]
     Customer# 928: Fr leaves and his total wait time is 6
 935: []
    ServiceArea#1  929:2
 936: []
    ServiceArea#1  929:1
 937: []
     Customer# 929: So leaves and his total wait time is 8
 938: []
    ServiceArea#1  -1:-1
 939: []
    ServiceArea#1  -1:-1
 940: []
    ServiceArea#1  -1:-1
 941: []
    ServiceArea#1  -1:-1
 
  new customer 942: Se
 942: []
    ServiceArea#1  942:6
 943: []
    ServiceArea#1  942:5
 944: []
    ServiceArea#1  942:4
 945: []
    ServiceArea#1  942:3
 
  new customer 946: Ju
 946: [946: Ju]
    ServiceArea#1  942:2
 947: [946: Ju]
    ServiceArea#1  942:1
 948: [946: Ju]
     Customer# 942: Se leaves and his total wait time is 6
 949: []
    ServiceArea#1  946:2
 950: []
    ServiceArea#1  946:1
 951: []
     Customer# 946: Ju leaves and his total wait time is 5
 952: []
    ServiceArea#1  -1:-1
 
  new customer 953: Fr
 953: []
    ServiceArea#1  953:4
 954: []
    ServiceArea#1  953:3
 
  new customer 955: So
 955: [955: So]
    ServiceArea#1  953:2
 
  new customer 956: So
 956: [955: So, 956: So]
    ServiceArea#1  953:1
 957: [955: So, 956: So]
     Customer# 953: Fr leaves and his total wait time is 4
 958: [956: So]
    ServiceArea#1  955:6
 959: [956: So]
    ServiceArea#1  955:5
 
  new customer 960: Ju
 960: [960: Ju, 956: So]
    ServiceArea#1  955:4
 
  new customer 961: Ju
 961: [960: Ju, 956: So, 961: Ju]
    ServiceArea#1  955:3
 962: [960: Ju, 956: So, 961: Ju]
    ServiceArea#1  955:2
 963: [960: Ju, 956: So, 961: Ju]
    ServiceArea#1  955:1
 964: [960: Ju, 956: So, 961: Ju]
     Customer# 955: So leaves and his total wait time is 9
 965: [961: Ju, 956: So]
    ServiceArea#1  960:5
 966: [961: Ju, 956: So]
    ServiceArea#1  960:4
 967: [961: Ju, 956: So]
    ServiceArea#1  960:3
 968: [961: Ju, 956: So]
    ServiceArea#1  960:2
 969: [961: Ju, 956: So]
    ServiceArea#1  960:1
 970: [961: Ju, 956: So]
     Customer# 960: Ju leaves and his total wait time is 10
 971: [956: So]
    ServiceArea#1  961:5
 972: [956: So]
    ServiceArea#1  961:4
 973: [956: So]
    ServiceArea#1  961:3
 974: [956: So]
    ServiceArea#1  961:2
 975: [956: So]
    ServiceArea#1  961:1
 
  new customer 976: Fr
 976: [956: So, 976: Fr]
     Customer# 961: Ju leaves and his total wait time is 15
 977: [976: Fr]
    ServiceArea#1  956:5
 978: [976: Fr]
    ServiceArea#1  956:4
 979: [976: Fr]
    ServiceArea#1  956:3
 
  new customer 980: So
 980: [980: So, 976: Fr]
    ServiceArea#1  956:2
 981: [980: So, 976: Fr]
    ServiceArea#1  956:1
 
  new customer 982: Se
 982: [982: Se, 976: Fr, 980: So]
     Customer# 956: So leaves and his total wait time is 26
 983: [980: So, 976: Fr]
    ServiceArea#1  982:2
 984: [980: So, 976: Fr]
    ServiceArea#1  982:1
 
  new customer 985: Ju
 985: [985: Ju, 976: Fr, 980: So]
     Customer# 982: Se leaves and his total wait time is 3
 986: [980: So, 976: Fr]
    ServiceArea#1  985:3
 
  new customer 987: Ju
 987: [987: Ju, 976: Fr, 980: So]
    ServiceArea#1  985:2
 988: [987: Ju, 976: Fr, 980: So]
    ServiceArea#1  985:1
 989: [987: Ju, 976: Fr, 980: So]
     Customer# 985: Ju leaves and his total wait time is 4
 990: [980: So, 976: Fr]
    ServiceArea#1  987:4
 
  new customer 991: Se
 991: [991: Se, 976: Fr, 980: So]
    ServiceArea#1  987:3
 
  new customer 992: Fr
 992: [991: Se, 976: Fr, 980: So, 992: Fr]
    ServiceArea#1  987:2
 993: [991: Se, 976: Fr, 980: So, 992: Fr]
    ServiceArea#1  987:1
 994: [991: Se, 976: Fr, 980: So, 992: Fr]
     Customer# 987: Ju leaves and his total wait time is 7
 995: [980: So, 976: Fr, 992: Fr]
    ServiceArea#1  991:2
 996: [980: So, 976: Fr, 992: Fr]
    ServiceArea#1  991:1
 997: [980: So, 976: Fr, 992: Fr]
     Customer# 991: Se leaves and his total wait time is 6
 998: [976: Fr, 992: Fr]
    ServiceArea#1  980:5
 
  new customer 999: Fr
 999: [976: Fr, 992: Fr, 999: Fr]
    ServiceArea#1  980:4
 1000: [976: Fr, 992: Fr, 999: Fr]
    ServiceArea#1  980:3
 1001: [976: Fr, 992: Fr, 999: Fr]
    ServiceArea#1  980:2
 1002: [976: Fr, 992: Fr, 999: Fr]
    ServiceArea#1  980:1
 1003: [976: Fr, 992: Fr, 999: Fr]
     Customer# 980: So leaves and his total wait time is 23
 1004: [992: Fr, 999: Fr]
    ServiceArea#1  976:2
 
  new customer 1005: Fr
 1005: [992: Fr, 999: Fr, 1005: Fr]
    ServiceArea#1  976:1
 
  new customer 1006: Se
 1006: [1006: Se, 992: Fr, 1005: Fr, 999: Fr]
     Customer# 976: Fr leaves and his total wait time is 30
 1007: [992: Fr, 999: Fr, 1005: Fr]
    ServiceArea#1  1006:1
 
  new customer 1008: Fr
 1008: [992: Fr, 999: Fr, 1005: Fr, 1008: Fr]
     Customer# 1006: Se leaves and his total wait time is 2
 1009: [999: Fr, 1008: Fr, 1005: Fr]
    ServiceArea#1  992:6
 1010: [999: Fr, 1008: Fr, 1005: Fr]
    ServiceArea#1  992:5
 1011: [999: Fr, 1008: Fr, 1005: Fr]
    ServiceArea#1  992:4
 
  new customer 1012: So
 1012: [1012: So, 999: Fr, 1005: Fr, 1008: Fr]
    ServiceArea#1  992:3
 1013: [1012: So, 999: Fr, 1005: Fr, 1008: Fr]
    ServiceArea#1  992:2
 
  new customer 1014: Se
 1014: [1014: Se, 1012: So, 1005: Fr, 1008: Fr, 999: Fr]
    ServiceArea#1  992:1
 
  new customer 1015: Ju
 1015: [1014: Se, 1012: So, 1015: Ju, 1008: Fr, 999: Fr, 1005: Fr]
     Customer# 992: Fr leaves and his total wait time is 23
 1016: [1015: Ju, 1012: So, 1005: Fr, 1008: Fr, 999: Fr]
    ServiceArea#1  1014:1
 1017: [1015: Ju, 1012: So, 1005: Fr, 1008: Fr, 999: Fr]
     Customer# 1014: Se leaves and his total wait time is 3
 1018: [1012: So, 999: Fr, 1005: Fr, 1008: Fr]
    ServiceArea#1  1015:4
 1019: [1012: So, 999: Fr, 1005: Fr, 1008: Fr]
    ServiceArea#1  1015:3
 
  new customer 1020: So
 1020: [1012: So, 1020: So, 1005: Fr, 1008: Fr, 999: Fr]
    ServiceArea#1  1015:2
 1021: [1012: So, 1020: So, 1005: Fr, 1008: Fr, 999: Fr]
    ServiceArea#1  1015:1
 1022: [1012: So, 1020: So, 1005: Fr, 1008: Fr, 999: Fr]
     Customer# 1015: Ju leaves and his total wait time is 7
 1023: [1020: So, 999: Fr, 1005: Fr, 1008: Fr]
    ServiceArea#1  1012:4
 
  new customer 1024: So
 1024: [1020: So, 1024: So, 1005: Fr, 1008: Fr, 999: Fr]
    ServiceArea#1  1012:3
 1025: [1020: So, 1024: So, 1005: Fr, 1008: Fr, 999: Fr]
    ServiceArea#1  1012:2
 1026: [1020: So, 1024: So, 1005: Fr, 1008: Fr, 999: Fr]
    ServiceArea#1  1012:1
 
  new customer 1027: So
 1027: [1020: So, 1024: So, 1027: So, 1008: Fr, 999: Fr, 1005: Fr]
     Customer# 1012: So leaves and his total wait time is 15
 1028: [1024: So, 999: Fr, 1027: So, 1008: Fr, 1005: Fr]
    ServiceArea#1  1020:3
 1029: [1024: So, 999: Fr, 1027: So, 1008: Fr, 1005: Fr]
    ServiceArea#1  1020:2
 1030: [1024: So, 999: Fr, 1027: So, 1008: Fr, 1005: Fr]
    ServiceArea#1  1020:1
 1031: [1024: So, 999: Fr, 1027: So, 1008: Fr, 1005: Fr]
     Customer# 1020: So leaves and his total wait time is 11
 1032: [1027: So, 999: Fr, 1005: Fr, 1008: Fr]
    ServiceArea#1  1024:4
 1033: [1027: So, 999: Fr, 1005: Fr, 1008: Fr]
    ServiceArea#1  1024:3
 
  new customer 1034: Fr
 1034: [1027: So, 999: Fr, 1005: Fr, 1008: Fr, 1034: Fr]
    ServiceArea#1  1024:2
 1035: [1027: So, 999: Fr, 1005: Fr, 1008: Fr, 1034: Fr]
    ServiceArea#1  1024:1
 1036: [1027: So, 999: Fr, 1005: Fr, 1008: Fr, 1034: Fr]
     Customer# 1024: So leaves and his total wait time is 12
 
  new customer 1037: Fr
 1037: [999: Fr, 1008: Fr, 1005: Fr, 1034: Fr, 1037: Fr]
    ServiceArea#1  1027:2
 1038: [999: Fr, 1008: Fr, 1005: Fr, 1034: Fr, 1037: Fr]
    ServiceArea#1  1027:1
 1039: [999: Fr, 1008: Fr, 1005: Fr, 1034: Fr, 1037: Fr]
     Customer# 1027: So leaves and his total wait time is 12
 
  new customer 1040: So
 1040: [1040: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  999:2
 1041: [1040: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  999:1
 
  new customer 1042: So
 1042: [1040: So, 1005: Fr, 1042: So, 1034: Fr, 1008: Fr, 1037: Fr]
     Customer# 999: Fr leaves and his total wait time is 43
 1043: [1042: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  1040:5
 1044: [1042: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  1040:4
 1045: [1042: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  1040:3
 1046: [1042: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  1040:2
 
  new customer 1047: So
 1047: [1042: So, 1005: Fr, 1047: So, 1034: Fr, 1008: Fr, 1037: Fr]
    ServiceArea#1  1040:1
 1048: [1042: So, 1005: Fr, 1047: So, 1034: Fr, 1008: Fr, 1037: Fr]
     Customer# 1040: So leaves and his total wait time is 8
 1049: [1047: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  1042:4
 1050: [1047: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  1042:3
 1051: [1047: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  1042:2
 1052: [1047: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
    ServiceArea#1  1042:1
 1053: [1047: So, 1005: Fr, 1037: Fr, 1034: Fr, 1008: Fr]
     Customer# 1042: So leaves and his total wait time is 11
 1054: [1005: Fr, 1008: Fr, 1037: Fr, 1034: Fr]
    ServiceArea#1  1047:5
 1055: [1005: Fr, 1008: Fr, 1037: Fr, 1034: Fr]
    ServiceArea#1  1047:4
 1056: [1005: Fr, 1008: Fr, 1037: Fr, 1034: Fr]
    ServiceArea#1  1047:3
 1057: [1005: Fr, 1008: Fr, 1037: Fr, 1034: Fr]
    ServiceArea#1  1047:2
 1058: [1005: Fr, 1008: Fr, 1037: Fr, 1034: Fr]
    ServiceArea#1  1047:1
 1059: [1005: Fr, 1008: Fr, 1037: Fr, 1034: Fr]
     Customer# 1047: So leaves and his total wait time is 12
 1060: [1008: Fr, 1034: Fr, 1037: Fr]
    ServiceArea#1  1005:4
 1061: [1008: Fr, 1034: Fr, 1037: Fr]
    ServiceArea#1  1005:3
 1062: [1008: Fr, 1034: Fr, 1037: Fr]
    ServiceArea#1  1005:2
 1063: [1008: Fr, 1034: Fr, 1037: Fr]
    ServiceArea#1  1005:1
 1064: [1008: Fr, 1034: Fr, 1037: Fr]
     Customer# 1005: Fr leaves and his total wait time is 59
 1065: [1034: Fr, 1037: Fr]
    ServiceArea#1  1008:4
 
  new customer 1066: Se
 1066: [1066: Se, 1037: Fr, 1034: Fr]
    ServiceArea#1  1008:3
 1067: [1066: Se, 1037: Fr, 1034: Fr]
    ServiceArea#1  1008:2
 1068: [1066: Se, 1037: Fr, 1034: Fr]
    ServiceArea#1  1008:1
 
  new customer 1069: So
 1069: [1066: Se, 1069: So, 1034: Fr, 1037: Fr]
     Customer# 1008: Fr leaves and his total wait time is 61
 1070: [1069: So, 1037: Fr, 1034: Fr]
    ServiceArea#1  1066:1
 
  new customer 1071: Se
 1071: [1071: Se, 1069: So, 1034: Fr, 1037: Fr]
     Customer# 1066: Se leaves and his total wait time is 5
 1072: [1069: So, 1037: Fr, 1034: Fr]
    ServiceArea#1  1071:3
 
  new customer 1073: Fr
 1073: [1069: So, 1037: Fr, 1034: Fr, 1073: Fr]
    ServiceArea#1  1071:2
 1074: [1069: So, 1037: Fr, 1034: Fr, 1073: Fr]
    ServiceArea#1  1071:1
 
  new customer 1075: Fr
 1075: [1069: So, 1037: Fr, 1034: Fr, 1073: Fr, 1075: Fr]
     Customer# 1071: Se leaves and his total wait time is 4
 1076: [1034: Fr, 1037: Fr, 1075: Fr, 1073: Fr]
    ServiceArea#1  1069:4
 1077: [1034: Fr, 1037: Fr, 1075: Fr, 1073: Fr]
    ServiceArea#1  1069:3
 
  new customer 1078: So
 1078: [1078: So, 1034: Fr, 1075: Fr, 1073: Fr, 1037: Fr]
    ServiceArea#1  1069:2
 1079: [1078: So, 1034: Fr, 1075: Fr, 1073: Fr, 1037: Fr]
    ServiceArea#1  1069:1
 1080: [1078: So, 1034: Fr, 1075: Fr, 1073: Fr, 1037: Fr]
     Customer# 1069: So leaves and his total wait time is 11
 1081: [1034: Fr, 1037: Fr, 1075: Fr, 1073: Fr]
    ServiceArea#1  1078:3
 1082: [1034: Fr, 1037: Fr, 1075: Fr, 1073: Fr]
    ServiceArea#1  1078:2
 1083: [1034: Fr, 1037: Fr, 1075: Fr, 1073: Fr]
    ServiceArea#1  1078:1
 1084: [1034: Fr, 1037: Fr, 1075: Fr, 1073: Fr]
     Customer# 1078 leaves and his total wait time is 6
 1085: [1037: Fr, 1073: Fr, 1075: Fr]
    ServiceArea#1  1034:3
 1086: [1037: Fr, 1073: Fr, 1075: Fr]
    ServiceArea#1  1034:2
 1087: [1037: Fr, 1073: Fr, 1075: Fr]
    ServiceArea#1  1034:1
 1088: [1037: Fr, 1073: Fr, 1075: Fr]
     Customer# 1034 leaves and his total wait time is 54
 1089: [1073: Fr, 1075: Fr]
    ServiceArea#1  1037:1
 1090: [1073: Fr, 1075: Fr]
     Customer# 1037 leaves and his total wait time is 53
 1091: [1075: Fr]
    ServiceArea#1  1073:1
 1092: [1075: Fr]
     Customer# 1073 leaves and his total wait time is 19
 1093: []
    ServiceArea#1  1075:5
 1094: []
    ServiceArea#1  1075:4
 1095: []
    ServiceArea#1  1075:3
 1096: []
    ServiceArea#1  1075:2
 1097: []
    ServiceArea#1  1075:1
 1098: []
     Customer# 1075 leaves and his total wait time is 23
 Customer		Total		Longest		Average Wait
 Senior 			 49			55			9.612244897959183
 Junior 			 51			39			9.607843137254902
 Sophomore 		 53			61			12.641509433962264
 Freshman 		 71			125			32.7887323943662
    
 ******************************************/