#ifndef QUEUES_HPP
#define QUEUES_HPP

#include "Task.hpp"
// #include "Global.hpp"
#include <queue>        //std::queue, std::priority_queue
#include <vector>       //std::vector

extern float avg_Job_Size;

struct Event{
  float time = 0.0; //The time at which the event will be performed
  bool create   = false;
  bool IO_done  = false;
  bool CPU_done = false;
  bool stop     = false;
  Task* whichTask;
};

struct IO{
  std::queue<Task*> IOQueue;
};

class Queues{
public:
  std::queue<Task*> readyQueue;

  struct GreaterThanByTime{
    bool operator()(const Event& lhs, const Event& rhs) const {
      return lhs.time > rhs.time;
    }
  };
  std::priority_queue<Event, std::vector<Event>, GreaterThanByTime> eventPriorityQueue;

  struct ShortestTaskFirst{
    bool operator()(const Task* lhs, const Task* rhs) const {
      return lhs->taskList.front().duration > rhs->taskList.front().duration;
    }
  };
  std::priority_queue<Task*, std::vector<Task*>, ShortestTaskFirst> readyPriorityQueue;

  struct ApproximateShortestTaskFirst{
    bool operator()(const Task* lhs, const Task* rhs) const{
      return (lhs->taskList.front().duration + avg_Job_Size) / 2 > (rhs->taskList.front().duration + avg_Job_Size) / 2;
    }
  };
  std::priority_queue<Task*, std::vector<Task*>, ApproximateShortestTaskFirst> readyPriorityQueueASJF;

  IO* io;
  IO* newIOq();
  Queues(){}
};
#endif
