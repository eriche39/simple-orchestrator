# Simple Orchestrator

Simple Orchestrator is a java library that can simplify your application development.

## Features that just done that:

###1. Promote modulization and isolation:
    Create your functional blocks and services as Tasks.
    Task can only sees it's context, such isolated from everything else.

###2. Productivity:
    Task creation is extreamly simple, just implement an execute method.
    No data packaging/unpackage required.
    No need to create your own processes, just use the default OrchestratorImpl.

###3. Code reusability:
    Task can be added into any orchestrator as long as the orchestrator's context is compatible.
    Create an orchestrator context is simple and like an entity class.

###4. Capable of selectively excute/skip tasks. a selector is simple to implement.

###5. Support uniform ways to handle error and events.

###6. Support asynchronous tasks.
    the orchestrator execute needs to wait for an async task to finish only when another task
    requres it's output, such provides high efficiency, and data availability are guaranteed.

###7. Flexible:
    Easy swap in a replacement task, even dynamically select a version by using selector.
    Add gadget task for monitoring, logging, delaying etc.


##The sample project shows most of it's power.

## License

See the [LICENSE](LICENSE.md) file for license rights and limitations (MIT).