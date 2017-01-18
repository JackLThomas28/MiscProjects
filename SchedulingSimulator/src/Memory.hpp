#ifndef MEMORY_HPP
#define MEMORY_HPP

class Memory{
private:
  int numberOfPages;
  void init();

  class Page{
  public:
    int lastUsed;
    int logicalPageNumber;
    bool secondChance;
    Page(int lpn){
      lastUsed = 0;
      secondChance = false;
      logicalPageNumber = lpn;
    }
  };
public:
  Memory(int numberOfPages);
  void clearArray();
  void updateArraySize();
  Page** pageArray;


};

#endif
