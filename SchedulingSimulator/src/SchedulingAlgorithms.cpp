#include <iostream> //std::cerr
#include "SchedulingAlgorithms.hpp"
#include "Global.hpp"
#include "Scheduling.hpp"
#include "Task.hpp"
#include "MemoryAlgorithms.hpp"

//The ready state for FIFO algorithm
void readyFIFO(Task* temp){
  if(temp->taskList.front().type == CPU_TYPE){
    q.readyQueue.push(temp);
    if(idle_CPUs > 0)popReadyFIFO();
  }
  else if (temp->taskList.front().type == IO_TYPE){
    int IOindex = temp->taskList.front().resource;
    IOQ_vector[IOindex].push(temp);
    popIO(IOindex);
  }
}

bool checkMemoryAlgorithm(int cpuResouce){
  bool addCachePenalty = false;
  if(memory_algorithm == MEMORYFIFO)
    addCachePenalty = memoryFIFO(cpuResouce);
  else if(memory_algorithm == MEMORYLRU)
    addCachePenalty = memoryLRU(cpuResouce);
  else if(memory_algorithm == MEMORYMRU)
    addCachePenalty = memoryMRU(cpuResouce);
  else if(memory_algorithm == MEMORYSC)
    addCachePenalty = memorySC(cpuResouce);
  return addCachePenalty;
}

//Take the next item on the readyQueue and schedule it
void popReadyFIFO(){
  Task* temp = q.readyQueue.front();
  q.readyQueue.pop();

  if(temp->taskList.size() == 1){
    temp->latency = current_Time - temp->creationTime;
    latency_Times.push_back(temp->latency);
  }
  CPU_utilization += temp->taskList.front().duration;

  bool addCachePenalty = checkMemoryAlgorithm(temp->taskList.front().resource);
  
  Event newEvent;
  if(addCachePenalty)
      newEvent.time = temp->taskList.front().duration + switch_Cost + current_Time + cache_Miss_Penalty;
  else
    newEvent.time = temp->taskList.front().duration + switch_Cost + current_Time;
  newEvent.whichTask = temp;
  newEvent.CPU_done = true;

  temp->taskList.erase(temp->taskList.begin());
  q.eventPriorityQueue.push(newEvent);
  runSchedule();
}

//The ready state for Shortest Job First algorithm
void readySJF(Task* temp){
  if(temp->taskList.front().type == CPU_TYPE){
    q.readyPriorityQueue.push(temp);
    if(idle_CPUs > 0)popReadySJF();
  }
  else if (temp->taskList.front().type == IO_TYPE){ //Does the IO need to be a priority queue?
    int IOindex = temp->taskList.front().resource;
    IOQ_vector[IOindex].push(temp);
    popIO(IOindex);
  }
}

//Take the next item on the readyPriorityQueue and schedule it
void popReadySJF(){
  Task* temp = q.readyPriorityQueue.top();
  q.readyPriorityQueue.pop();

  if(temp->taskList.size() == 1){
    temp->latency = current_Time - temp->creationTime;
    latency_Times.push_back(temp->latency);
  }
  CPU_utilization += temp->taskList.front().duration;

  bool addCachePenalty = checkMemoryAlgorithm(temp->taskList.front().resource);

  Event newEvent;
  if(addCachePenalty)
      newEvent.time = temp->taskList.front().duration + switch_Cost + current_Time +  cache_Miss_Penalty;
  else
    newEvent.time = temp->taskList.front().duration + switch_Cost + current_Time;
  newEvent.whichTask = temp;
  newEvent.CPU_done = true;

  temp->taskList.erase(temp->taskList.begin());
  q.eventPriorityQueue.push(newEvent);
  runSchedule();
}

//The ready state for Round Robin algorithm
void readyRR(Task* temp){
  if(temp->taskList.front().type == CPU_TYPE){
    q.readyQueue.push(temp);
    if(idle_CPUs > 0)popReadyRR();
  }
  else if (temp->taskList.front().type == IO_TYPE){
    int IOindex = temp->taskList.front().resource;
    IOQ_vector[IOindex].push(temp);
    popIO(IOindex);
  }
}

//Take the next item off of the readyQueue and schedule it
void popReadyRR(){
  Task* temp = q.readyQueue.front();
  q.readyQueue.pop();

  if(temp->taskList.front().duration - CPU_runtime <= 0){
    if(temp->taskList.size() == 1){
      temp->latency = current_Time - temp->creationTime;
      latency_Times.push_back(temp->latency);
    }

    bool addCachePenalty = checkMemoryAlgorithm(temp->taskList.front().resource);

    Event newEvent;
    if(addCachePenalty)
        newEvent.time = temp->taskList.front().duration + switch_Cost + current_Time + cache_Miss_Penalty;
    else
      newEvent.time = temp->taskList.front().duration + switch_Cost + current_Time;
    newEvent.whichTask = temp;
    newEvent.CPU_done = true;
    q.eventPriorityQueue.push(newEvent);

    CPU_utilization += temp->taskList.front().duration;
    temp->taskList.erase(temp->taskList.begin());
  }
  else{
    temp->taskList.front().duration = temp->taskList.front().duration - CPU_runtime;
    CPU_utilization += CPU_runtime;
    ready(temp);
  }
  runSchedule();
}

//Take the next item off of the ready priority queue and schedule it
void readyASJF(Task* temp){
  if(temp->taskList.front().type == CPU_TYPE){
    q.readyPriorityQueueASJF.push(temp);
    if(idle_CPUs > 0)popReadyASJF();
  }
  else if (temp->taskList.front().type == IO_TYPE){ //Does the IO need to be a priority queue?
    int IOindex = temp->taskList.front().resource;
    IOQ_vector[IOindex].push(temp);
    popIO(IOindex);
  }
}

//The ready state for Approximate Shortest Job First algorithm
void popReadyASJF(){
  Task* temp = q.readyPriorityQueueASJF.top();
  q.readyPriorityQueueASJF.pop();

  avg_Job_Size = (temp->taskList.front().duration + avg_Job_Size) / 2;

  if(temp->taskList.size() == 1){
    temp->latency = current_Time - temp->creationTime;
    latency_Times.push_back(temp->latency);
  }
  CPU_utilization += temp->taskList.front().duration;

  bool addCachePenalty = checkMemoryAlgorithm(temp->taskList.front().resource);

  Event newEvent;
  if(addCachePenalty)
      newEvent.time = temp->taskList.front().duration + switch_Cost + current_Time + cache_Miss_Penalty;
  else
    newEvent.time = temp->taskList.front().duration + switch_Cost + current_Time;
  newEvent.whichTask = temp;
  newEvent.CPU_done = true;

  temp->taskList.erase(temp->taskList.begin());
  q.eventPriorityQueue.push(newEvent);
  runSchedule();
}
