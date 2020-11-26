import exception.ImpossibleBottleNeckValueException;
import exception.ImpossibleOrderException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Paede
 * @version 12.11.2020
 */
public class MaxFlowTest {

    public static ArrayList<DPoint> points = new ArrayList<DPoint>();

    @Before
    public void init(){
        MaxFlow.sRev = 0;
        points.clear();
    }


    @Test
    public void maxFlowTestDiagram1() throws ImpossibleOrderException, ImpossibleBottleNeckValueException {
        points.add(new DPoint('s', new ArrayList<String>(Arrays.asList("a5", "c10"))));
        points.add(new DPoint('a', new ArrayList<String>(Arrays.asList("b4", "d5"))));
        points.add(new DPoint('b', new ArrayList<String>(Arrays.asList("d6", "t4"))));
        points.add(new DPoint('c', new ArrayList<String>(Arrays.asList("a6", "d5"))));
        points.add(new DPoint('d', new ArrayList<String>(Collections.singletonList("t12"))));
        Assert.assertEquals(14, MaxFlow.maxFlow(MaxFlow.pointToEdges(points)));
    }

    @Test
    public void maxFlowTestDiagram2() throws ImpossibleOrderException, ImpossibleBottleNeckValueException {
        points.add(new DPoint('s', new ArrayList<String>(Arrays.asList("b3", "a10"))));
        points.add(new DPoint('a', new ArrayList<String>(Collections.singletonList("d9"))));
        points.add(new DPoint('b', new ArrayList<String>(Arrays.asList("e4", "c2"))));
        points.add(new DPoint('c', new ArrayList<String>(Collections.singletonList("f5"))));
        points.add(new DPoint('d', new ArrayList<String>(Collections.singletonList("e8"))));
        points.add(new DPoint('e', new ArrayList<String>(Arrays.asList("a3", "t3", "c1"))));
        points.add(new DPoint('f', new ArrayList<String>(Collections.singletonList("t8"))));
        Assert.assertEquals(6, MaxFlow.maxFlow(MaxFlow.pointToEdges(points)));
    }

    @Test
    public void maxFlowTestDiagram3() throws ImpossibleOrderException, ImpossibleBottleNeckValueException {
        points.add(new DPoint('s', new ArrayList<String>(Arrays.asList("a10", "c10"))));
        points.add(new DPoint('a', new ArrayList<String>(Arrays.asList("b4", "d8"))));
        points.add(new DPoint('b', new ArrayList<String>(Collections.singletonList("t10"))));
        points.add(new DPoint('c', new ArrayList<String>(Collections.singletonList("d9"))));
        points.add(new DPoint('d', new ArrayList<String>(Arrays.asList("b6", "t10"))));
        Assert.assertEquals(19, MaxFlow.maxFlow(MaxFlow.pointToEdges(points)));
    }

}