package effectivejava3.generics;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 21:20 2018/10/21
 * @Modified By:
 */
public class Favorites {
    private Map<Class<?>, Object> favorites = new HashMap<>();
    public <T> void putFavorite(Class<T> type, T instance) {
        // Achieving runtime type safety with a dynamic cast
        this.favorites.put(Objects.requireNonNull(type), type.cast(instance));
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(this.favorites.get(type));
    }

    public static void main(String[] args) {
        Favorites favorites = new Favorites();
        favorites.putFavorite(String.class, "jfasdf");
        favorites.putFavorite(Integer.class, 1);
        String sFa = favorites.getFavorite(String.class);
        System.out.println(favorites.getFavorite(String.class));
        System.out.println(favorites.getFavorite(Integer.class));

        List<String> strings = new ArrayList<>();
        favorites.putFavorite(List.class, strings);
        strings = favorites.getFavorite(List.class);
    }
}
