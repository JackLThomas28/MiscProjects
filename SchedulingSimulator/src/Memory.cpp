#include "Memory.hpp"
#include "Global.hpp"

Memory::Memory(int pageCount){
  numberOfPages = pageCount;
  init();
}

void Memory::init(){
  pageArray = new Page*[numberOfPages];
  for(int i = 0; i < cache_Size; i++){
    pageArray[i] = new Page(-1);
  }
}

void Memory::clearArray(){
  int i = 0;
  while(this->pageArray[i] != NULL){
    this->pageArray[i] = new Page(-1);
    i++;
  }
}

void Memory::updateArraySize(){
  pageArray = new Page*[cache_Size];
  for(int i = 0; i < cache_Size; i++){
    pageArray[i] = new Page(-1);
  }
}
