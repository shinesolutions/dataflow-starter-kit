package com.shinesolutions.dataflow;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.shinesolutions.dataflow.transforms.AnotherTestTransform;
import com.shinesolutions.dataflow.transforms.BadArgumentsTransform;
import com.shinesolutions.dataflow.transforms.NamedMethodTransform;
import com.shinesolutions.dataflow.transforms.WrongMethodTransform;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by garethjones on 24/11/16.
 */
public class AnnotatedTransformFinderTest {

    @Test
    public void shouldReturnTransformMethodOfNamedClass() {
        AnnotatedTransformFinder finder = new AnnotatedTransformFinder();
        Method actual = finder.find("test");
        assertNotNull("should find the method", actual);
        assertEquals("should be called transform", "transform", actual.getName());
        assertEquals("should return void", Void.TYPE, actual.getReturnType());
        assertEquals("should have one parameter", 1, actual.getParameterCount());
        assertEquals("should have a Pipeline parameter", Pipeline.class, actual.getParameterTypes()[0]);
    }

    @Test
    public void shouldBeCaseInsensitiveOnName() {
        AnnotatedTransformFinder finder = new AnnotatedTransformFinder();
        Method actual = finder.find("TeSt");
        assertNotNull("should find the method", actual);
    }

    @Test
    public void shouldReturnNullIfNoTransformCouldBeFound() {
        AnnotatedTransformFinder finder = new AnnotatedTransformFinder();
        Method actual = finder.find("does not exist");
        assertNull("should not find the method", actual);
    }

    @Test
    public void shouldReturnNullIfAnnotatedClassHasNoTransformMethod() {
        assertNotNull(AnotherTestTransform.class.getAnnotation(DataflowTransformer.class));

        AnnotatedTransformFinder finder = new AnnotatedTransformFinder();
        Method actual = finder.find("test-without-method");
        assertNull("should not find the method", actual);
    }

    @Test
    public void shouldReturnNullIfAnnotatedClassHasNoMatchingMethod() {
        assertNotNull(WrongMethodTransform.class.getAnnotation(DataflowTransformer.class));
        AnnotatedTransformFinder finder = new AnnotatedTransformFinder();
        Method actual = finder.find("wrong-method");
        assertNull("should not find the method", actual);
    }

    @Test
    public void shouldReturnNullIfAnnotatedClassMethodTakesWrongArguments() {
        assertNotNull(BadArgumentsTransform.class.getAnnotation(DataflowTransformer.class));
        AnnotatedTransformFinder finder = new AnnotatedTransformFinder();
        Method actual = finder.find("bad-arguments");
        assertNull("should not find the method", actual);
    }

    @Test
    public void shouldReturnMethodMatchingNamedMethodInAnnotation() {
        assertNotNull(NamedMethodTransform.class.getAnnotation(DataflowTransformer.class));
        AnnotatedTransformFinder finder = new AnnotatedTransformFinder();
        Method actual = finder.find("named-method");
        assertNotNull("should find the method", actual);
        assertEquals("should be called rumplestiltskin", actual.getName(), "rumplestiltskin");
        assertEquals("should return void", Void.TYPE, actual.getReturnType());
        assertEquals("should have one parameter", 1, actual.getParameterCount());
        assertEquals("should have a Pipeline parameter", Pipeline.class, actual.getParameterTypes()[0]);
    }

}