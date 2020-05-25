package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        List<Neighbour> expectedFavoritesNeighbours = DI.getNeighbourApiService().getFavoriteNeighbours();
        assertThat(favoriteNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedFavoritesNeighbours.toArray())));
    }

    @Test
    public void createNeighbourWithSuccess() {
        Neighbour celia = new Neighbour(612, "Célia", "https://i.pravatar.cc", "Somewhere over the rainbow",
                "+33606060606", "I like chocolate");
        service.createNeighbour(celia);
        assertTrue(service.getNeighbours().contains(celia));
    }


    @Test
    public void deleteFavoriteNeighbourWithSuccess() {
        Neighbour celia = new Neighbour(612, "Célia", "https://i.pravatar.cc", "Somewhere over the rainbow",
                "+33606060606", "I like chocolate");

        service.createNeighbour(celia);
        service.changeFavoriteStatus(612);

        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();

        Neighbour neighbour = favoriteNeighbours.get(0);

        service.changeFavoriteStatus(612);

        favoriteNeighbours = service.getFavoriteNeighbours();

        assertFalse(favoriteNeighbours.contains(celia));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }
}
