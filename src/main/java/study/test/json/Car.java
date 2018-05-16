package study.test.json;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

/**
 * Created by fankun on 2018/3/29.
 */
public class Car {
    private String no;
    private Person driver;

    public Car(){

    }

    public Car(String no, String name) {
        this.no = no;
        driver = new Person(name);
    }

    public Car(String no, Person driver) {
        this.no = no;
        this.driver = driver;
    }

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    class Person{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person(String name) {
            this.name = name;
        }

        public Person(){}
    }

    @Override
    public String toString() {
        return "Car{" +
                "no='" + no + '\'' +
                ", driver=" + driver +
                '}';
    }

    public static void main(String[] args) throws IOException {
        String s = "{\"no\":\"volvo\",\n" +
                "\"driver\":{\n" +
                "\"name\":\"fk\"\n" +
                "}}";
        Map<Integer,Car> carMap = Maps.newHashMap();
        carMap.put(1,new Car("c","fk"));
        carMap.put(2,new Car("d","fk2"));
//        Car c = JSON.parseObject(s,Car.class);
        ObjectMapper mapper = new ObjectMapper();
        String mapstr = mapper.writeValueAsString(carMap);

        Map<Integer,Car> m = mapper.readValue(mapstr, new TypeReference<Map<Integer,Car>>() {});
        System.out.println(m.get(1).getDriver().getName());
    }
}
