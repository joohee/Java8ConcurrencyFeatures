package net.joey.reflect;

import lombok.extern.slf4j.Slf4j;
import net.joey.reflect.example.RentCar;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created with Java8ConcurrencyFeatures.
 * User: neigie
 * Date: 2016. 10. 5.
 * Time: 오후 6:04
 * To change this template use File | Settings | File Templates.
 * <p>
 * This is not about java.concurrent package, but it's an important feature about Java.
 * <p>
 * https://examples.javacodegeeks.com/core-java/reflection/java-reflection-example/
 */
@Slf4j
public class ReflectMain {

    public static void main(String[] args) {

        Class rental = RentCar.class;

        String rentalClassPackage = rental.getName();
        log.info("Class Name is {}", rentalClassPackage);

        String rentalClassNoPackage = rental.getSimpleName();
        log.info("Class Name without package: {}", rentalClassNoPackage);

        Package rentalPackage = rental.getPackage();
        log.info("Package Name is {}", rentalPackage);

        Constructor[] constructors = rental.getConstructors();
        log.info("Constructors are: {}", Arrays.toString(constructors));

        try {
            Constructor constructor = rental.getConstructor(Integer.TYPE);
            try {
                RentCar rent = (RentCar) constructor.newInstance(455);

                Method[] allmethods = rental.getMethods();
                log.info("Methods are: {}", Arrays.toString(allmethods));
                for (Method method : allmethods) {
                    log.info("\tMethod = {}", method.getName());
                }

                Method[] declaredMethod = rental.getDeclaredMethods();
                log.info("Declared methods are: {}", Arrays.toString(declaredMethod));
                for (Method dmethod : declaredMethod) {
                    log.info("\tMethod = {}", dmethod.getName());
                }

                Method oneMethod = rental.getMethod("computeRentalCost",
                        new Class[]{Integer.TYPE});
                log.info("Method is {}", oneMethod);

                // rent a car during 4 days
                oneMethod.invoke(rent, 4);

                Class[] parameterTypes = oneMethod.getParameterTypes();
                log.info("Parameter types of computeRentalCost() are: {}", Arrays.toString(parameterTypes));

                Class returnType = oneMethod.getReturnType();
                log.info("Return type is {}", returnType);

                Field[] fields = rental.getFields();
                log.info("Public Fields are: ");

                for (Field oneField : fields) {
                    Field field = rental.getField(oneField.getName());
                    String fieldName = field.getName();
                    log.info("Fieldname is {}", fieldName);

                    Object fieldType = field.getType();
                    log.info("Type of field {} is {}", fieldName, fieldType);

                    Object value = field.get(rent);
                    log.info("Value of field {} is {}", fieldName, value);

                }

                // How to access private member fields of the class.
                Field[] fieldsWithPrvate = rental.getDeclaredFields();
                log.info("Fields with private: ");
                for (Field field : fieldsWithPrvate) {
                    log.info("field: {}", field.getName());
                }

                Field privateField = rental.getDeclaredField("type");
                String name = privateField.getName();
                log.info("One private Fieldname is {}", name);
                // it's possible only for reflection.
                // if you don't set below one line, you'll get 'java.lang.IllegalAccessException'
                privateField.setAccessible(true);

                String fieldValue = (String)privateField.get(rent);
                log.info("fieldValue: {}", fieldValue);


            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }


}
