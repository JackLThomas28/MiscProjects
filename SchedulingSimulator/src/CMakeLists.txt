set(CMAKE_CXX_FLAGS "-std=c++14 -std=c++11 -Wall")

set(srcs
	main.cpp
	Scheduling.cpp
	Global.cpp
 	SchedulingAlgorithms.cpp
 	Queues.cpp
 	Task.cpp
	Memory.cpp
	MemoryAlgorithms.cpp
    )

add_executable(assignment9 ${srcs})
