package com.project.bookbook.controller;

public class FavoriteResponse {
	private boolean isFavorite;

    public FavoriteResponse(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
