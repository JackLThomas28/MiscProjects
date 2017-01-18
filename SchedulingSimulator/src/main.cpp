#include <iostream> //std::cin, std::cout, std::cerr
#include "Global.hpp"
#include "Queues.hpp"
#include "Scheduling.hpp"
#include "SchedulingAlgorithms.hpp"
#include "Task.hpp"
#include "Memory.hpp";

//Prints the menu allows for input to change default values
void printMenu(){
  std::string algorithmMenu = "Pick Your Scheduling Algorithm:\n1. FIFO\n2. Shortest Job First\n3. Round Robin\n4. Approximate Shortest Job First\n";
  std::cout << algorithmMenu;
  int schedulintChoice;
  std::cin >> algorithm;
  algorithm += 4;
  if(algorithm == RR){
    std::cout << "CPU runtime = ";
    std::cin  >> CPU_runtime;
  }
  std::string memoryAlgorithmMenu = "Pick Your Memory Algorithm:\n1. Random(FIFO)\n2. Least Recently Used\n3. Most Recently Used\n4. Second Chance\n";
  std::cout << memoryAlgorithmMenu;
  std::cin  >> memory_algorithm;
  memory_algorithm += 9;

  std::cout << "Default Values:\n";
  std::cout << "CPU count = 2\nIO Devices = 1\nTask Mix (The percentage of tasks that is CPU bound) = 50%\nSwitch Cost = 1.0\n";
  std::cout << "Task Frequency = 5.0\nPage Distribution upper bound = 10\nCache Size = 10\nCache Miss Penalty = 1.0\n";
  std::cout << "Would you like to change the default values? (y/n) ";
  char response;
  std::cin >> response;
  if(response == 'y'){
    std::cout << "CPU count = ";
    std::cin  >> CPUcount;
    std::cout << "IO Devices = ";
    std::cin  >> IO_devices;
    std::cout << "Task Mix(%) = ";
    std::cin  >> task_Mix;
    std::cout << "Switch Cost = ";
    std::cin  >> switch_Cost;
    std::cout << "Task Frequency = ";
    std::cin  >> task_Frequency;
    std::cout << "Page Distribution Upper Bound = ";
    std::cin  >> page_Distribution;
    std::cout << "Cache Size = ";
    std::cin  >> cache_Size;
    std::cout << "Cache Miss Penalty = ";
    std::cin  >> cache_Miss_Penalty;
    std::cout << std::endl;
  }
}

float findAvg(std::vector<float> v){
	auto sum = 0.0;
	auto avg = 0.0;
	for(int i = 0; i < v.size(); i++){
		sum += v[i];
	}
	avg = sum/v.size();

	return avg;
}

void storeResults(float throughput){
  avgResponseTimes.push_back(findAvg(response_Times));
  avgLatencyTimes.push_back(findAvg(latency_Times));
  avgEfficiency.push_back((current_Time / CPU_utilization) * 100);
  avgThroughPut.push_back(throughput);
  avgCacheMisses.push_back(cache_Miss);
}

void reset(float& throughput){
  m.clearArray();
  throughput = 0;
  cache_Miss = 0;
  current_Time = 0;
  CPU_utilization = 0;
  idle_CPUs = CPUcount;
}

//Prints the resulting efficiency and throughput of the program
void printResults(){
  std::cout << "Average Response Time\n";
  std::cout << findAvg(avgResponseTimes) << std::endl;

  std::cout << "Average Latency Time\n";
  std::cout << findAvg(avgLatencyTimes) << std::endl;

  std::cout << "Average Efficiency\n";
  std::cout << findAvg(avgEfficiency) << std::endl;

  std::cout << "Average Throughput\n";
  std::cout << findAvg(avgThroughPut) << std::endl;

  std::cout << "Average Number of Cache Misses\n";
  std::cout << findAvg(avgCacheMisses) << std::endl;
}

int main(){
  CPUcount       = 2;
  task_Mix       = 50; //The percentage of tasks that is CPU Bound
  switch_Cost    = 1.0;
  task_Frequency = 5.0;
  IO_devices     = 1;
  idle_CPUs = CPUcount;
  float throughput;
  task_Mix = task_Mix/10;
  cache_Size = 25;
  cache_Miss_Penalty = 1.0;
  page_Distribution = 50;
  m.updateArraySize();

  printMenu();

  for(int i = 0; i < IO_devices; i++){
    IO* temp = q.newIOq();
    IOQ_vector.push_back(temp->IOQueue);
  }

  for(int i = 0; i < 4; i++){
    initSchedule();
    runSchedule();
    throughput = 10 / current_Time; //10 is for the number of Jobs that were completed
    storeResults(throughput);
    reset(throughput);
  }
  printResults();

  return 0;
}
