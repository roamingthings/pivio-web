package io.pivio.view.overview.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SoftwareDependencyTest {

    private SoftwareDependency A = new SoftwareDependency("A", "1", new ArrayList<>());
    private SoftwareDependency A_1 = new SoftwareDependency("A", "1", new ArrayList<>());
    private SoftwareDependency B = new SoftwareDependency("B", "1", new ArrayList<>());

    @Test
    public void testCompareSmaller() throws Exception {
        assertThat(B.compareTo(A)).isEqualTo(1);
    }

    @Test
    public void testCompareGreater() throws Exception {
        assertThat(A.compareTo(B)).isEqualTo(-1);
    }

    @Test
    public void testCompareEqual() throws Exception {
        assertThat(A_1.compareTo(A)).isEqualTo(0);
    }

}