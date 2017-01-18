#include "Scheduling.hpp"
#include <iostream> //std::cin, std::cout, std::cerr
#include "Global.hpp"
#include "SchedulingAlgorithms.hpp"
#include "Task.hpp"

//Adds the multiple create events to the priority queue and one stop event...
// as the last event
void initSchedule(){
  for(int i = 0; i < 12; i++){
    if(i == 11){
      Event newEvent;
      newEvent.time = i * task_Frequency;
      newEvent.stop = true;
      q.eventPriorityQueue.push(newEvent);
    }
    else{
      Event newEvent;
      newEvent.time = i * task_Frequency;
      newEvent.create = true;
      q.eventPriorityQueue.push(newEvent);
    }
  }
}

//Take the next item on the IOQueue and schedule
void popIO(int whichIO){
  Task* temp = IOQ_vector[whichIO].front();
  IOQ_vector[whichIO].pop();

  if(!temp->firstIOComplete){
    temp->firstIOComplete = true;
    temp->responseTime = current_Time - temp->creationTime;
    response_Times.push_back(temp->responseTime);
  }

  Event newEvent;
  newEvent.time = temp->taskList.front().duration + switch_Cost + current_Time;
  newEvent.whichTask = temp;
  newEvent.IO_done = true;

  temp->taskList.erase(temp->taskList.begin());
  q.eventPriorityQueue.push(newEvent);
  runSchedule();
}

//Depending on what the next item is, place it in the readyQueue or appropriate IOQueue
void ready(Task* temp){
  if(temp->taskList.empty())return;
  if(algorithm == FIFO){
    readyFIFO(temp);
  }
  else if(algorithm == SJF){
    readySJF(temp);
  }
  else if(algorithm == ASJF){
    readyASJF(temp);
  }
  else if(algorithm == RR){
    readyRR(temp);
  }
}

//Create a new task
void init(){
  Task* newTask = new Task(IO_devices, task_Mix, current_Time, page_Distribution);
  if(idle_CPUs > 0) ready(newTask);
}

//Process the items on the Event priority queue
void runSchedule(){
  while(!q.eventPriorityQueue.empty()){
    Event currentEvent = q.eventPriorityQueue.top();
    q.eventPriorityQueue.pop();
    current_Time = currentEvent.time;
    if (currentEvent.create){
      std::cerr<<"Create time: " << current_Time<<std::endl;
      init();
    }
    else if (currentEvent.CPU_done){
      std::cerr<<"CPU Done " << current_Time<<std::endl;
      idle_CPUs++;
      ready(currentEvent.whichTask);
    }
    else if (currentEvent.IO_done){
      std::cerr<<"IO Done "<< current_Time<<std::endl;
      ready(currentEvent.whichTask);
    }
		else if (currentEvent.stop){
      std::cerr<<"Program Finished "<< current_Time<<std::endl;
      // while(!q.eventPriorityQueue.empty()){
      //   q.eventPriorityQueue.pop();
      // }
    }
	}
}
