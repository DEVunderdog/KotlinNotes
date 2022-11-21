// Sealed Classes

//if class A is declared in
//the File1.kt file and class B is declared in the File2.kt file, then class B or any
//other class can extend class A. This is a normal practice and is referred to as inheritance.
//However, Kotlin provides another type of class that is restricted to limited classes and
//cannot be further inherited. These classes are called sealed classes.

sealed class A(val number: Int){
    class B(n: Int): A(n){
        fun display(){
            println("number: $number")
        }
    }

    class C(n:Int): A(n){
        fun square(){
            println("Square: " + number*number)
        }
    }
}

//class A is a sealed class and class B and class C are the classes that are inherited from
//class A. Before Kotlin 1.1, declaring a child class inside a sealed class was the only
//method of declaration. Now, however, we can extend the sealed class from outside the class
//body, as shown in the preceding example, where class C extends class A. Remember
//that both the sealed and the child classes must be declared in the same file:

class D(n:Int) : A(n){
    fun cube(){
        println("number: " + number*number*number)
    }
}

// Sealed Classes are implicitly abstract classes which means that the classes which are used keyword sealed i.e. sealed
// classes are never going to be instantiated but they work as abstract idea for the classes drawn from it.

class Order(val item: String)

sealed class OrderDelivery(val order: Order)

class ReceivedAtDepot(val depotName: String, order: Order): OrderDelivery(order)

class Dispatched(var truckId: String, var driverName: String, order: Order): OrderDelivery(order)

class Delivered(var destination: String, var isDelivered : Boolean, order:Order): OrderDelivery(order)

fun orderStatus(delivery:OrderDelivery){
    when (delivery){
        is ReceivedAtDepot -> println("${delivery.order.item} is received at ${delivery.depotName} depot.")
        is Dispatched -> println("${delivery.order.item} is dispatched, Truck ID is ${delivery.truckId} and driver is ${delivery.driverName}")
        is Delivered -> println("${delivery.order.item} delivered at ${delivery.destination}.\n" + "Delivery to customer = ${delivery.isDelivered}.\n")
    }
}

// Enum Classes

// Concept of enumeration is needed for a type to have a only certain values.
// Enum constants aren't just collections of constants - these have properties, methodes etc.
// Each of enum constants acts as separate instance of the class and separated by commas
// An instance of enum class cannot be created using constructors

// Example

enum class Days{
    Sunday,
    Monday,
    Tuesday,
    Wednesday,
    Thurday,
    Friday,
    Saturday
}
// Initializing enums

enum class Cards(val color: String){
    Diamond("black"),
    Heart("red")
}

val color = Cards.Diamond.color

// Enums Properties and Methods

// Properties:
//1) ordinal - This property stores the ordinal value of the constant, which is usually a zero-based index
//2) name - This property stores the name of the constant

// Methods:
// values - This method returns a list of all the constants defined withing the enum class
// valueOf - This method returns the enum constant defined in enum, matching the input string. If the constant,
//           is not present in the enum, then an IllegalArgumentException is thrown.

fun mainEnum(){
    for (day in Days.values()){
        println("${day.ordinal} = ${day.name}")
    }

    println("${Days.valueOf("Wednesday")}")
}

// Enum and Interfaces

interface printable{
    fun show()
}

enum class News : printable{
    North{
        override fun show(){
            println("This is function body.")
        }
    }
}

// Singleton Classes
// Declared using object
// Cannot Create instance of this class
// Declare a constructor with this class

object MyButton{
    var count = 0
    fun clickMe(){
        println("I have been clicked ${++count} times")
    }
}

// It is called singleton class because we create single instance of this class using object keyword which will
// be called using class name only.

// Object class with inheritance and interfaces

open class Parent{
    open fun callMySingleton(){
        println("Parent class is called")
    }
}

object MySingleton: Parent(){
    override fun callMySingleton(){
        super.callMySingleton()
        println("My singleton class is called")
    }
}

interface buttonInterface{
    fun clickMe()
}

object MyButton01: buttonInterface{
    var count = 0
    override fun clickMe(){
        println("I have been clicked ${++count} times")
    }
}

// One of the main use of object class is that its called globally anytime a sort of class is needed you
// aren't require to create a object and call it. Also, you don't create object or class according to scope.
// Because you can create this class on the basis of the global scope and then you don't need to create the
// object which removes the objectification of using local scope so hence you can access that class at global
// scope and that meant to access things globally.

// Companion Object
//Other programming languages, including Java and C#, allow us to use static variables and
//static functions. We can declare functions as static when they are utility functions that
//should be accessible without having to create a class object. Kotlin, however, does not
//provide a static keyword for a variable or a function. Instead, it allows us to add a
//companion object inside the class to declare a static function or variable.

class Parent01{
    companion object{
        const val count = 10
        fun companionFunction(){
            println("I am your companion")
        }
    }

    fun getCompanion(){
        companionFunction()
    }

    fun memberFunction(){
        println("I am member function")
    }
}

//We can also assign a name to the companion object. This doesn't make any difference, apart
//from improving the readability of the code. However, just because Kotlin allows us to
//assign a name to the companion object doesn't mean that we can declare another
//companion object in the same class with a different name. Kotlin only allows
//one companion object per class:

/*
class Parent{
    companion object StaticName{
        const val count = 10
        fun companionFunction(){
            println("I am your companion")
        }
    }
}
*/



fun main(args: Array<String>) {
    var b = A.B(1)
    b.display()
    var c = A.C(2)
    c.square()
    var d = D(3)
    d.cube()


//Declaring an object in a class that is declared within a sealed class body isn't as clean as
//declaring an object in a class that is declared outside of the sealed class:

    fun status(a: A) {
        when (a) {
            is A.B -> a.display()
            is A.C -> a.square()
            is D -> a.cube()
        }
    }

    fun statusShock(a: A) {
        when (a) {
            is A.B -> a.display()
            is A.C -> a.square()
            else -> {
                println("unknown")
            }
        }
    }

// Real Life Application for Sealed Example

    var book = Order("OOP in Kotlin Book")
    var atDepot = ReceivedAtDepot("Stockholm City", order = book)
    var dispatched = Dispatched("AXV-122", "Logan", order = book)
    var delivered = Delivered(destination = "New York City", isDelivered = true, order = book)

    var orderDeliverySteps = listOf(atDepot, dispatched, delivered)
    for (step in orderDeliverySteps) {
        orderStatus(step)
    }

    var knife = Order("Kitchen Knife Sets")
    atDepot = ReceivedAtDepot("Stockholm City", order = knife)
    dispatched = Dispatched(truckId = "JVY-354", "Peter Parker", order = knife)
    delivered = Delivered("Arkansas", true, order= knife)
    var orderDeliveryProcedure = listOf(atDepot, dispatched, delivered)
    for(step in orderDeliveryProcedure){
        orderStatus(step)
    }

    val week = Days.valueOf("Tuesday")
    println("Item type: " + week)
    println("Name: ${week.name}")


    mainEnum()

    MyButton.clickMe()

    // You don't need to create object for instantiation of object class

    MySingleton.callMySingleton()

    MyButton01.clickMe()

    // Companion Object
    Parent01.companionFunction()
    println(Parent01.count)
    val obj = Parent01()
    obj.memberFunction()
    obj.getCompanion()

}
