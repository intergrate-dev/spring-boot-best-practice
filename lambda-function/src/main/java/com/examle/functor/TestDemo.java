package com.example.functor;

import com.huawei.rewards.example.entity.Address;
import com.huawei.rewards.example.entity.Employee;
import com.huawei.rewards.example.entity.IM;
import com.huawei.rewards.example.entity.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TestDemo
 *
 * @author xxx
 * @since 2023/9/20 14:23
 */
@Slf4j
public class TestDemo {

    public static void main(String[] args) {
//        testFunctorSimple();
//        testFunctor();
//        flatmap();
        flatmapByMonad();
    }

    private static void flatmapByMonad() {
        User tom = new User("Tom");
        User mike = new User("Mike");
        User alice = new User("Alice");
        User jack = new User("Jack");
        User john = new User("John");
        User rose = new User("Rose");
        User ben = new User("Ben");

        tom.addFriends(mike, alice);
        mike.addFriends(jack, john, rose);
        alice.addFriends(rose, ben);
        jack.addFriends(rose, john);
        john.addFriends(rose, ben);
        rose.addFriends(tom, mike, alice);
        ben.addFriends(rose, jack);

//        查tom这个用户的朋友的朋友了：
        tom.getFriends().stream().flatMap(user -> user.getFriends().stream()).forEach(user -> log.info(user.toString()));
        // 查tom这个用户朋友的朋友的朋友：
        tom.getFriends().stream().flatMap(user -> user.getFriends().stream()).flatMap(user -> user.getFriends().stream()).forEach((user -> log.info(user.toString())));
    }

    private static void flatmap() {
        List<List<Integer>> lists = new ArrayList<>();
        lists.add(Arrays.asList(1));
        lists.add(Arrays.asList(2, 3));
        lists.add(Arrays.asList(4, 5, 6));

        lists.stream().flatMap((list) -> list.stream()).map(i -> i + 1).forEach(i -> log.info(String.valueOf(i)));

    }

    private static void testFunctorSimple() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        list.stream().map(i -> i + 1).forEach((i) -> log.info("i: {}", i));
        Integer sum = list.stream().map(i -> i * i).reduce(Integer::max).get();
        log.info("sum: {}", sum);

        IM qq = new IM();
        qq.setType(1);
        qq.setCode("123223");

        Address address = new Address();
        address.setProvince("浙江");
        address.setCity("杭州");
        address.setDistrict("西湖");
        address.setDetail("西湖国家广告产业园");

        Employee zhangsan = new Employee();
        zhangsan.setEmployNo(23);
        zhangsan.setFirstName("张");
        zhangsan.setLastName("三");
        zhangsan.setCommunication(qq);
        zhangsan.setAddress(address);

        CommonFunctor<Employee> employeeCommonFunctor = new CommonFunctor<>(zhangsan);
        String fullName = employeeCommonFunctor.map(employee -> employee.getFirstName() + employee.getLastName()).getValue();
        String fullAddress = employeeCommonFunctor.map(Employee::getAddress).map(addr -> addr.getProvince()+addr.getCity()+addr.getDistrict()+addr.getDetail()).getValue();

        log.info("fullName: {}, fullAddress: {}", fullName, fullAddress);
    }

    @SneakyThrows
    private static void testFunctor() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Tom", 45, 20));
        employees.add(new Employee(3, "Alice", 28, 3));
        employees.add(new Employee(5, "Mike", 25, 1));
        employees.add(new Employee(9, "Jack", 36, 11));
        employees.add(new Employee(120, "Sam", 50, 25));
        employees.add(new Employee(56, "Rose", 32, 7));
        employees.add(new Employee(77, "Andy", 39, 14));

        int totalHolidayCount =
                new StreamIfUtil<>(employees.stream())
                        .select(e -> e.getWorkAge() < 10)
                        .with(e -> e.setHolidayCount(5))
                        .select(e -> e.getWorkAge() >= 10 && e.getWorkAge() < 20)
                        .with(e -> e.setHolidayCount(10))
                        .elseWith(e -> e.setHolidayCount(20))
                        .getStream()
                        .map(e -> e.getHolidayCount())
                        .reduce(Integer::sum)
                        .get();
        log.info("totalCount: {}", totalHolidayCount);
    }
}
