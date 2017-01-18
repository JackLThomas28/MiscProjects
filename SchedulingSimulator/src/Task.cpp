#include "Task.hpp"

float randomNumberGenerator(int lower, int upper){
  std::random_device rd;
  std::mt19937 mt(rd());
  std::uniform_real_distribution<float> dis(lower, upper);
  return dis(mt);
}

const int CPU_TYPE = 0;
const int IO_TYPE  = 1;

Task::Task(int IOcount, int taskMix, float currentTime, int pageDistribution){
  init(IOcount, taskMix, pageDistribution);
  creationTime = currentTime;
}

void Task::init(int IOcount, int taskMix, int pageDistribution){
  int taskListSize = ((rand() % 5) + 1) * 2 + 1; //generating a random, odd number of individual tasks between 1 and 11

  for(int i = 0; i < taskListSize; i++){
    IndividualTask newTask;

    int temp = i;
    if(temp % 2 == 0){
      newTask.resource = randomNumberGenerator(0, pageDistribution);
      newTask.type = CPU_TYPE;
      if(task_index <= taskMix)
      newTask.duration = randomNumberGenerator(0, 5) * 2;
      else
      newTask.duration = randomNumberGenerator(0, 5);
    }
    else{
      newTask.resource = randomNumberGenerator(0, IOcount);
      newTask.type = IO_TYPE;
      if(task_index > taskMix)
      newTask.duration = randomNumberGenerator(0, 5) * 2;
      else
      newTask.duration = randomNumberGenerator(0, 5);
    }
    taskList.push_back(newTask);
  }
  task_index++;
}
