#include "MemoryAlgorithms.hpp"
#include <iostream> //std::cerr
#include "Memory.hpp"
#include "Global.hpp"

int currIndex = 0;

//Linearly searches the page array
//returns -1 if there is a miss, otherwise it returns the index of the found CPUresource
int findCPUBurst(int CPUresource){
  for(int i = 0; i < cache_Size; i++){
    if(m.pageArray[i]->logicalPageNumber == CPUresource){
      return i;
    }
  }
  return -1;
}

//Memory scheduling: Random(FIFO) Algorithm
//returns true if there was a miss, returns false otherwise
bool memoryFIFO(int CPUresource){
  int index = findCPUBurst(CPUresource);
  if(index == -1){
    std::cerr << "Cache Miss\n";
    if(currIndex == cache_Size)
      currIndex = 0;
    m.pageArray[currIndex]->logicalPageNumber = CPUresource;
    currIndex++;
    cache_Miss++;
    return true;
  }
  return false;
}

//Memory scheduling: Least Recently Used Algorithm
//Replaces the least recently used page in the cache with a new one
//returns true if there was a miss, returns false otherwise
bool memoryLRU(int CPUresource){
  int index = findCPUBurst(CPUresource);
  if(index == -1){
    std::cerr << "Cache Miss\n";
    int least = m.pageArray[0]->lastUsed;
    int leastIndex = 0;
    for(int i = 0; i < cache_Size; i++){
      if(least > m.pageArray[i]->lastUsed){
        least = m.pageArray[i]->lastUsed;
        leastIndex = i;
      }
    }
    m.pageArray[leastIndex]->logicalPageNumber = CPUresource;
    m.pageArray[leastIndex]->lastUsed = 0;
    cache_Miss++;
    return true;
  }
  else{
    m.pageArray[index]->lastUsed++;
    return false;
  }
}

//Memory scheduling: Most Recently Used Algorithm
//Replaces the most recently used page in the cache with a new one
//returns true if there was a miss, returns false otherwise
bool memoryMRU(int CPUresource){
  int index = findCPUBurst(CPUresource);
  if(index == -1){
    std::cerr << "Cache Miss\n";
    int mostRecent = m.pageArray[0]->lastUsed;
    int mostIndex = 0;
    for(int i = 0; i < cache_Size; i++){
      if(mostRecent < m.pageArray[i]->lastUsed){
        mostRecent = m.pageArray[i]->lastUsed;
        mostIndex = i;
      }
    }
    m.pageArray[mostIndex]->logicalPageNumber = CPUresource;
    m.pageArray[mostIndex]->lastUsed = 0;
    cache_Miss++;
    return true;
  }
  else{
    m.pageArray[index]->lastUsed++;
    return false;
  }
}

//Memory scheduling: Second Chance Algorithm
//If there is a miss and the next page has a second chance, move on to the next page
//returns true if there was a miss, returns false otherwise
bool memorySC(int CPUresource){
  int index = findCPUBurst(CPUresource);
  if(index == -1){
    std::cerr << "Cache Miss\n";
    if(currIndex == cache_Size)
      currIndex = 0;
    while(m.pageArray[currIndex]->secondChance){
      m.pageArray[currIndex]->secondChance = false;
      currIndex++;
      if(currIndex == cache_Size)
        currIndex = 0;
    }
    m.pageArray[currIndex]->logicalPageNumber = CPUresource;
    currIndex++;
    cache_Miss++;
    return true;
  }
  else{
    m.pageArray[index]->secondChance = true;
    return false;
  }
}
