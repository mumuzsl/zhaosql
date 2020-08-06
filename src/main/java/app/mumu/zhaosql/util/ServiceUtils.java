package app.mumu.zhaosql.util;

import org.springframework.data.domain.ExampleMatcher;

import java.util.HashMap;
import java.util.Map;

public class ServiceUtils {

    private ServiceUtils() {
    }

    public static <I, E> Map<String, Object> convertToMap(String[] keys, I i, E e) {
        Map<String, Object> map = new HashMap<>();

        map.put(keys[0], i);
        map.put(keys[1], e);

        return map;
    }

    public static Integer page(Integer page) {
        return (page == null || page < 1) ? 0 : page - 1;
    }

    public static Integer page2(Integer page) {
        return (page == null || page < 0) ? 0 : page;
    }

    public static ExampleMatcher buildExampleMatcher(String field) {
        return ExampleMatcher.matching().withMatcher(field, ExampleMatcher.GenericPropertyMatchers.contains());
    }

    public static ExampleMatcher buildContainsExampleMatcher(String... strings) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        for (String s : strings) {
            exampleMatcher = exampleMatcher.withMatcher(s, ExampleMatcher.GenericPropertyMatchers.contains());
        }
        return exampleMatcher;
    }

    public static ExampleMatcher buildContainsExampleMatcher(ExampleMatcher exampleMatcher, String field) {
        return exampleMatcher.withMatcher(field, ExampleMatcher.GenericPropertyMatchers.contains());
    }
}
