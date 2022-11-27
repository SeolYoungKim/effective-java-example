package chapter_05.item33;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 타입 안전 이종 컨테이너 패턴 각 타입의 Class 객체를 매개변수화한 키 역할로 사용하는 패턴 class의 클래스가 제네릭이기 때문에 가능하다. class 리터럴의 타입은
 * Class가 아닌 Class<T>다
 * - String.class == Class<String>
 * - Integer.class == Class<Integer>
 * 컴파일 타임 타입 정보와, 런타임 타입 정보를 알아내기 위해 메서드들이 주고 받는 class 리터럴을 "타입 토큰"이라고 한다.
 * 제약 조건 : 실체화 불가 타입에는 사용할 수 없다. String, String[]은 사용할 수 있어도, List<String>은 사용할 수 없다.
 * List<String>용 class는 얻을 수 없기 때문이다.
 */
public class Favorites {

    // 이렇게 할 경우 확실히 유연해진다! 여러가지 타입의 원소를 담을 수 있다.
    // 맵 안의 값은 key의 타입과 항상 일치한다.
    private final Map<Class<?>, Object> favorites = new HashMap<>();  // key - val 간의 타입 관계를 보증하지 않는다.

    public <T> void putFavorite(Class<T> type, T instance) {  // 메서드를 이렇게 구성하여 타입 안전하다.
        // 확인 작업을 추가하면 타입 불변식을 어기는 일이 없도록 보장할 수 있다.
        favorites.put(Objects.requireNonNull(type), type.cast(instance));
    }

    public <T> T getFavorite(Class<T> type) {
        // Class의 cast 메서드로 객체 참조를 Class 객체가 가리키는 타입으로 동적 형변환 해줌.
        // 넘겨받은 타입으로 map에서 꺼내온 Object를 형변환!
        // cast 메서드는 형변환 연산자의 동적 버전이다.
        // Class는 제네릭이라는 이점을 완벽히 활용한다. 그래서 cast 메서드를 사용함.
        return type.cast(favorites.get(type));  // 하지만 해당 메서드에서 타입 관계를 되살릴 수 있다.
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Favorites f = new Favorites();

        f.putFavorite(String.class, "Java");
        f.putFavorite(Integer.class, 0xcafebabe);
        f.putFavorite(Class.class, Favorites.class);

        String favoriteString = f.getFavorite(String.class);
        Integer favoriteInteger = f.getFavorite(Integer.class);
        Class<?> favoriteClass = f.getFavorite(Class.class);

        System.out.printf("%s %x %s%n", favoriteString, favoriteInteger, favoriteClass.getName());


        // 슈퍼 타입 토큰 -> spring 프레임워크에서는 아예 "ParameterizedTypeReference"라는 클래스로 구현해놓았다.
        // 이를 이용하면 제네릭 타입도 문제 없이 저장할 수 있다.
        List<String> pets = List.of("개", "고양이", "앵무");
        String typeName = pets.getClass().getGenericSuperclass().getTypeName();
        System.out.println("typeName = " + typeName);

        Type genericSuperclass = pets.getClass().getGenericSuperclass();
        System.out.println("genericSuperclass = " + genericSuperclass);

        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        System.out.println("parameterizedType = " + parameterizedType);

        Type rawType = parameterizedType.getRawType();
        System.out.println("rawType = " + rawType);

        Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
        System.out.println("actualTypeArgument = " + actualTypeArgument);

        Map<String, String> map = Map.of("히히", "호호");
        Type mapType = map.getClass().getGenericSuperclass();
        ParameterizedType mapParameterizedType = (ParameterizedType) mapType;
        Type actualTypeArgumentOfMap = mapParameterizedType.getActualTypeArguments()[0];
        Type actualTypeArgumentOfMap2 = mapParameterizedType.getActualTypeArguments()[1];
        System.out.println("actualTypeArgumentOfMap = " + actualTypeArgumentOfMap);
        System.out.println("actualTypeArgumentOfMap2 = " + actualTypeArgumentOfMap2);
//
//        f.putFavorite(new TypeRef<List<String>>(){}, pets);
//        f.getFavorite(new TypeRef<List<String>>(){});

        // Class<?> -> Class<? extends E>로 형변환 하는 방법!
        Class<?> aClass = Class.forName("java.lang.String");
        Class<? extends String> aClass1 = aClass.asSubclass(String.class);
    }

}
