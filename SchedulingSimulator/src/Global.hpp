#ifndef GLOBAL_HPP
#define GLOBAL_HPP

#include "Queues.hpp"
#include "Task.hpp"
#include "Memory.hpp"

extern int const FIFO;     //First In First Out
extern const int SJF;      //Shortest Job First scheduling algorithm
extern const int RR;       //Round Robin
extern const int ASJF;     //Approximate Shortest Job First scheduling algorithm
extern int algorithm;      //The scheduling algorithm chosen
extern float CPU_runtime;  //How long each CPU is allowed to run during RR scheduling

extern int cache_Size;        //The number of logical pages
extern int page_Distribution; //The upper bound for the page distribution
extern int cache_Miss;        //The total times a cache miss occurs
extern float cache_Miss_Penalty; //The penalty for a cache miss
extern Memory m;
extern const int MEMORYFIFO;  //Memory algorithm fifo
extern const int MEMORYLRU;   //Memory algorithm least recently used
extern const int MEMORYMRU;   //Memory algorithm most recently used
extern const int MEMORYSC;    //Memory algorithm second chance
extern int memory_algorithm;  //The chosen memory algorithm

extern float current_Time;      //The current time of the process in the priority queue
extern int idle_CPUs;           //The number of current CPUs not doing anything
extern float switch_Cost;       //The time it takes to switch from IO to CPU or vice versa
extern int task_Mix;            //The percentage of CPU bound tasks from the total 100% of tasks
extern float task_Frequency;    //The rate at which new processes are added to the system
extern int IO_devices;          //The number of total unique IO devices
extern float CPU_utilization;   //The time that a CPU was actually doing something
extern int CPUcount;            //The total number of CPUs

extern Queues q;
extern std::vector<std::queue<Task*> > IOQ_vector;
extern std::vector<float> latency_Times;
extern std::vector<float> response_Times;

extern std::vector<float> avgResponseTimes;
extern std::vector<float> avgEfficiency;
extern std::vector<float> avgThroughPut;
extern std::vector<float> avgCacheMisses;
extern std::vector<float> avgLatencyTimes;

#endif
