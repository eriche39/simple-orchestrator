# Visualization

Provides UI to visualize runtime orchestrator's structure

### Usage:
#### 1. add dependency to pom:
        <dependency>
            <groupId>com.github.eriche39</groupId>
            <artifactId>orchestrator-visualization</artifactId>
            <version>1.0</version>
        </dependency>
        
#### 2. add to ResourceConfig:
        register(AdminService.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
        
#### 3. browser to ../internal/orchestrators.html
