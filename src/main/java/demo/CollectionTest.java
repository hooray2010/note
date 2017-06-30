package demo;

import lol.model.skill.FlashSS;
import model.*;
import org.junit.Test;

import java.util.*;

/**
 * Created by hurui on 2017/6/29.
 */
public class CollectionTest {
  
  public static void main(String[] args) throws Exception {
    
  }
  
  /**
   * 自动向上转型
   * 强制向下转型
   * 子类继承父类所有
   */
  @Test
  public void testGeneric() {
    //集合泛型只可以限定上边界？
    List<? extends Driver> driverList = new ArrayList<>();
    //userList.add(new Driver());
    
    List<? super Driver> driverList2 = new ArrayList<>();
    driverList2.add(new Driver());
    //driverList2.add(new User());
    
    List<? super User> userList = new ArrayList<>();
    userList.add(new Driver());
    userList.add(new User());
    //子类向上转型，调用父类任何方法也不会报错
    //但是限定子类边界，调用子类的方法，（假如编译可以通过）其实父类没有！则会报错
    User driver = new Driver();
    userList.add(driver);
    System.out.println(userList);
    
    //调用的仍然为子类自己的方法（子类可以继承父类的所有方法），所有并不会报错
    User driverUser = (User) userList.get(0);
    System.out.println(driverUser.getName());
    System.out.println(driverUser.getSalary());
  }
  
  /**
   * TreeMap的key排序同TreeSet
   * TreeSet底层由TreeMap实现
   */
  @Test
  public void testTreeMap() {
    Map<Driver, String> driverMap = new TreeMap<>(new UserComparator());
    
    Driver taxiDriver = new Driver();
    taxiDriver.setCar("大众");
    taxiDriver.setAge(20);
    taxiDriver.setSalary(20000);
    driverMap.put(taxiDriver, "出租车司机");
    
    Driver busDriver = new Driver();
    busDriver.setCar("青年汽车");
    busDriver.setAge(40);
    busDriver.setSalary(10000);
    driverMap.put(busDriver, "大巴车司机");
    
    System.out.println(driverMap);
  }
  
  /**
   * TreeSet不能存入null，比较排序需要compare
   * HashSet可以存入null，无序用的哈希值
   * <p>
   * <? extends T>
   * 下边界为T，放入的父类
   * 接受对象的时候
   * <? super T>
   * 上边界为T，放入的子类
   * 调用方法的时候，子父类都实现类Comparable接口，方法重写；
   * <p>
   * 1、TreeMap容器内的对象必须实现Comparable接口
   * Comparable<? super K> k = (Comparable<? super K>) key，TreeMap中565行
   * 继承体系：Driver > User > Comparable，重写的方法为compareTo
   * Driver自动向上转型为Comparable，调用compareTo方法，实际为Driver自身的方法
   * 2、传入比较器，新的比较策略
   */
  @Test
  public void testTreeSet() {
    //TreeSet不能存入null，比较排序需要compare
    //HashSet可以存入null，无序用的哈希值
    Set<String> stringSet = new TreeSet<>();
    stringSet.add("C");
    stringSet.add("B");
    stringSet.add("a");
    System.out.println(stringSet);
    
    Set<String> stringOrderSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    stringOrderSet.add("C");
    stringOrderSet.add("B");
    stringOrderSet.add("a");
    System.out.println(stringOrderSet);
    
    //TreeMap容器内的对象必须实现Comparable接口
    User user1 = new User("老王", SexEnum.MALE, 40, 10000);
    User user2 = new User("小李", SexEnum.FEMALE, 20, 20000);
    
    Set<User> userSet = new TreeSet<>();
    userSet.add(user1);
    userSet.add(user2);
    System.out.println(userSet);
    
    //传入比较器，新的比较策略
    Set<User> userOrderSet = new TreeSet<>(new UserComparator());
//    userOrderSet.add(user1);
//    userOrderSet.add(user2);
    userOrderSet.addAll(userSet);
    System.out.println(userOrderSet);
    
    Driver driver1 = new Driver();
    driver1.setAge(40);
    Driver driver2 = new Driver();
    driver2.setAge(20);
    Set<Driver> driverSet = new TreeSet<>();
    driverSet.add(driver1);
    driverSet.add(driver2);
    System.out.println(driverSet);
  }
  
  /**
   * 比较器：策略模式
   */
  @Test
  public void testComparator() {
    List<String> stringList = new ArrayList<>();
    Collections.addAll(stringList, "C", "B", "a");
    System.out.println(stringList);
    stringList.sort(String.CASE_INSENSITIVE_ORDER);
    System.out.println(stringList);
    stringList.sort((s1, s2) -> {
      int n1 = s1.length();
      int n2 = s2.length();
      int min = Math.min(n1, n2);
      for (int i = 0; i < min; i++) {
        char c1 = s1.charAt(i);
        char c2 = s2.charAt(i);
        if (c1 != c2) {
          c1 = Character.toUpperCase(c1);
          c2 = Character.toUpperCase(c2);
          if (c1 != c2) {
            c1 = Character.toLowerCase(c1);
            c2 = Character.toLowerCase(c2);
            if (c1 != c2) {
              // No overflow because of numeric promotion
              return c2 - c1;
            }
          }
        }
      }
      return n2 - n1;
    });
    System.out.println(stringList);
  }
  
  /**
   * 字符的哈希值为ASCII值
   * 对象的地址值为：哈希值转16进制
   */
  @Test
  public void testHashCode2() {
    String s = "1";
    System.out.println("1的哈希值：" + s.hashCode());
    System.out.println("16进制为：" + Integer.toHexString(s.hashCode()));
    System.out.println(1 + '1');
    
    User user = new User();
    System.out.println("User的哈希值为：" + user.hashCode());
    System.out.println("16进制为：" + Integer.toHexString(user.hashCode()));
    
    System.out.println("People：");
    People people = new People();
    people.setAge(1);
    people.setSexEnum(SexEnum.MALE);
    System.out.println(people);
    
    System.out.println("People2：");
    People people2 = new People();
    people2.setAge(2);
    System.out.println(people2.hashCode());
    System.out.println(Integer.toHexString(people2.hashCode()));
    System.out.println(people2.toString());
    System.out.println(people2);
    
    System.out.println("------------------");
    //比较地址
    System.out.println(people == people2);
    //比较哈希值
    System.out.println(people.equals(people2));
    Set<People> peopleSet = new HashSet<>();
    peopleSet.add(people);
    peopleSet.add(people2);
    //重写equals方法
    System.out.println(peopleSet.size());
  }
  
  /**
   * 测试hashcode
   */
  @Test
  public void testHashCode() {
    //System.out.println(null);
    System.out.println("A hashcode：" + "A".hashCode());
    System.out.println("a hashcode：" + "a".hashCode());
    System.out.println("A".hashCode() + 32 == "a".hashCode());
    System.out.println('a' - 'A');
    System.out.println("flash".hashCode());
    System.out.println(new FlashSS().hashCode());
  }
  
}
