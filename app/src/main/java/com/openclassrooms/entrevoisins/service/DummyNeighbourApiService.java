package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * Loop getting all of the favorites and assigning it to a list
     */
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favoriteNeighboursList = new ArrayList<>();

        for(int i=0;i<neighbours.size();i++){
            if(neighbours.get(i).getFavorite()) favoriteNeighboursList.add(neighbours.get(i));
        }
        return favoriteNeighboursList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public void changeFavoriteStatus(long id) {
        for(Neighbour neighbour:getNeighbours()){
            if(neighbour.getId()==id)
                neighbour.setFavorite(!neighbour.getFavorite());
        }
    }
}
