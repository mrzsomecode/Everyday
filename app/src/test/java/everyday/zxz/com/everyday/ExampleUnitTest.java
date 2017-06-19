package everyday.zxz.com.everyday;

import org.junit.Test;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() throws Exception {
        Integer[] str1 = {1, 2, 3, 4, 5};
        String[] str2 = {"5", "4", "3", "2", "1", "0"};
        Observable.merge(Observable.fromArray(str1), Observable.fromArray(str2))
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(Serializable serializable) throws Exception {
                        System.out.println("merge  " + serializable);
                    }
                });
        Observable.zip(Observable.fromArray(str1), Observable.fromArray(str2),
                new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(Integer integer, String s) throws Exception {
                        System.out.println(integer + "  apply: " + s);
                        return s;
                    }
                }).subscribe(s -> System.out.println("  accept: " + s));
    }
}