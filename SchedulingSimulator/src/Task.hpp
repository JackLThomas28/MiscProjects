#ifndef TASK_HPP
#define TASK_HPP

#include <random>       //std::random_device, std::mt19937, std::uniform_real_distribution
#include <vector>       //std::vector

extern const int CPU_TYPE;
extern const int IO_TYPE;

extern float randomNumberGenerator(int lower, int upper);

struct IndividualTask{
  float duration = 0.0; //The time it takes to complete the task
  int resource;
  int type;
  bool done = false;
};

class Task{
private:
  void init(int IOcount, int taskMix, int pageDistribution);
  int task_index = 0;
public:
  bool firstIOComplete = false;
  float creationTime;
  float latency;
  float responseTime;

  std::vector<IndividualTask> taskList;
  Task(int IOcount, int taskMix, float currentTime, int pageDistribution);
};


#endif
