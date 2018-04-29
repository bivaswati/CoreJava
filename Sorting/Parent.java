package Sorting;

class Parent {
    void msg(){
        System.out.println("parent");
    }
}
/*
1. If the superclass method does not declare an exception,
subclass overridden method cannot declare the checked exception but can declare unchecked exception
2.Rule: If the superclass method declares an exception,
subclass overridden method can declare same, subclass exception or no exception but cannot declare parent exception.
*/

class TestExceptionChild extends Parent {
    //Compilation Error
    //void msg() throws IOException {
    //No Compilation Error bcoz ArithmeticException is unchecked exception.
    void msg() throws ArithmeticException{
        System.out.println("TestExceptionChild");
    }

    public static void main(String args[]) {
        Parent p = new TestExceptionChild();
        try {
            p.msg();
        } catch (Exception e) {
        }
    }
}

/*
import java.io.*;
class Parent{
  void msg()throws ArithmeticException{System.out.println("parent");}
}

class TestExceptionChild2 extends Parent{
  void msg()throws Exception{System.out.println("child");}

  public static void main(String args[]){
   Parent p=new TestExceptionChild2();
   try{
   p.msg();
   }catch(Exception e){}
  }
}

Output:Compile Time Error


*/

/*
class Parent{
  void msg()throws Exception{System.out.println("parent");}
}

class TestExceptionChild5 extends Parent{
  void msg(){System.out.println("child");}

  public static void main(String args[]){
   Parent p=new TestExceptionChild5();
   try{
   p.msg();
   }catch(Exception e){}
  }
}

Output- child
 */