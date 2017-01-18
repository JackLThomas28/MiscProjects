#include "Queues.hpp"

float avg_Job_Size = 0;

IO* Queues::newIOq(){
  IO* temp = new IO;
  return temp;
}
