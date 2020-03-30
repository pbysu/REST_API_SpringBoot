package me.bysu.restAPI.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(JUnitParamsRunner.class)
public class EventTest {

    @Test
    public void builder() {
        String name = "test event";
        Event event = Event.builder()
                .name(name)
                .description("ksug")
                .build();

        assertThat(event.getName()).isEqualTo(name);
    }

    @Test
    public void javaBean() {
        String name = "keesun";
        Event event = new Event();
        event.setName(name);
        assertThat(event.getName()).isEqualTo(name);
    }


    @Test
    @Parameters({
            "0, 0, true",
            "100, 0, false",
            "0, 100, false"
    })
    public void testFree(int basePrice, int maxPrice, boolean isFree){
        Event event= Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        event.update();

        assertThat(event.isFree()).isEqualTo(isFree);
    }


    private Object[] paramsForTestOffLine(){
        return new Object[] {
                new Object[] {"A-plus", true},
                new Object[] { null, false}
        };
    }

    @Test
    @Parameters(method = "paramsForTestOffLine")
    public void testOffLine(String location, boolean isOffLine){

        // Given
        Event event = Event.builder()
                .location(location)
                .build();
        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isEqualTo(isOffLine);

    }

}
