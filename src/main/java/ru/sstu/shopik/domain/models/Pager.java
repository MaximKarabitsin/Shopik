package ru.sstu.shopik.domain.models;

public class Pager {
    private int buttonToShow = 5;
    private int startPage;
    private int endPage;

    public Pager(int totalPages, int currentPage) {
        int halfPagesTtShow = getButtonToShow()/2;
        //if we have only 4 or less pages, we show all of them
        if (totalPages <= getButtonToShow()) {
            setStartPage(1);
            setEndPage(totalPages);
        } else {
            //if we stay on 1,2, we show first 5 pages
            if (currentPage - halfPagesTtShow <=0) {
                setStartPage(1);
                setEndPage(getButtonToShow());
            } else {
                // if we stay on pages, that less than last page by two, we show 5 last
                if (currentPage + halfPagesTtShow == totalPages) {
                    setStartPage(currentPage -halfPagesTtShow);
                    setEndPage(totalPages);
                } else {
                    //look at the previous block
                    if (currentPage + halfPagesTtShow > totalPages) {
                        setStartPage(totalPages-getButtonToShow()+1);
                        setEndPage(totalPages);
                    } else { //all another variants
                        setStartPage(currentPage-halfPagesTtShow);
                        setEndPage(currentPage+halfPagesTtShow);
                    }
                }
            }
        }
    }

    public int getButtonToShow() {
        return buttonToShow;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
}

