

    mermaidAPI.initialize({
        startOnLoad:false
    });
    $(function(){
        $.get( "../internal/meta", function( data ) {
            var element = document.querySelector("div.mermaid");

            var insertSvg = function(svgCode, bindFunctions){
                element.innerHTML = svgCode;

                if(typeof callback !== 'undefined'){
                    callback('svgGraph');
                }
                bindFunctions(element);
            };


            var graphDefinition = getGraphs(data);
            var graph = mermaidAPI.render('svgGraph', graphDefinition, insertSvg, element);
        });

        function getGraphs(data){
            var counter = {id:0};
            var graphs = data.map(function(orch){
                return getGraph(orch, counter).join("; ");
            });
            var str = "graph TB;\n "+graphs.join("; ");
            return str;
        }

        function getGraph(orch, counter){
            var lines = [];
            lines.push("subgraph "+orch.beanName);
            var prevNodes = new Array(addStartEnd("Start", lines, counter));
            orch.taskList.forEach(function(task){
                prevNodes = addTask(task, prevNodes, lines, counter);
            });
            var endId = addStartEnd("End", lines, counter);
            prevNodes.forEach(function(node){
                lines.push(node+"-->"+endId);
            });
            //end of subgraph
            lines.push("end");
            return lines;
        }

        function addStartEnd(nodeName, lines, counter){
            return addNode(nodeName, "(("+nodeName+"))", lines, counter);
        }

        function addNode(nodeName, label, lines, counter){
            counter.id = counter.id+1;
            var nodeId = nodeName+counter.id;
            var node = nodeId + label;
            lines.push(node);
            return nodeId;
        }

        //task: next node
        //prevNodes: previous nodes from
        //lines: output lines
        //key: key to this task
        //return array of new nodes ids
        function addTask(task, prevNodes, lines, counter, key){
            switch(task.type){
                case "CHANNEL":
                    task.taskList.forEach(function(subTask){
                        prevNodes = addTask(subTask, prevNodes, lines, counter, key);
                        key = "";
                    });
                    break;
                case "SELECTOR":
                    prevNodes = new Array(addTaskNode(task, prevNodes, lines, counter, key));
                    var newNodes = [];
                    for(var subKey in task.map) {
                        var subTask = task.map[subKey];
                        newNodes = newNodes.concat(addTask(subTask, prevNodes, lines, counter, subKey))
                    }
                    prevNodes = newNodes;
                    break;
                case "TASK":
                    prevNodes = new Array(addTaskNode(task, prevNodes, lines, counter, key));
                    break;
                default:
                    alert("supported addTask for type: "+task.type);
                    break;
            }
            return prevNodes;
        }

        //add node and link to lines for the task
        //task: task
        //prevNodes: previous nodes from
        //lines: output lines
        //key: key to this task
        //return new node id
        function addTaskNode(task, prevNodes, lines, counter, key){
            var label = "";
            var id = "";
            switch(task.type){
                case "CHANNEL":
                    alert("CHANNEL should not be visible");
                    break;
                case "SELECTOR":
                    label = "{"+task.beanName+"}";
                    break;
                case "TASK":
                    label = "("+task.beanName+")";
                    break;
                default:
                    alert("label is not supported for type: "+task.type);
                    break;
            }
            var id = addNode(task.type, label, lines, counter);
            prevNodes.forEach(function(prevNode){
                if(typeof key != 'undefined' && key.length>0){
                    lines.push(prevNode+"-->|"+key+"|"+id);
                } else {
                    lines.push(prevNode+"-->"+id);
                }
                var tooltip = getTooltip(task);
                lines.push('click '+id+' callback "<p align=\'left\'>'+ tooltip+'</p>"');
            });
            return id;
         }

         function getTooltip(task){
            if(task.type === 'SELECTOR'){
                task = $.extend(true, {}, task);
                var map = task.map;
                for(var key in map){
                    //stop the tree
                    map[key] = map[key].beanName || className;
                }
                task.map = JSON.stringify(map).replace(/[\"|\{|\}]/g, "").replace(/,/g,"<br/>&emsp;&emsp;");
            }
            return JSON.stringify(task).replace(/[\"|\{|\}]/g, "").replace(/,/g,"<br/>");
         }

         var callback = function(task){
            console.log(task);
         }
    });
