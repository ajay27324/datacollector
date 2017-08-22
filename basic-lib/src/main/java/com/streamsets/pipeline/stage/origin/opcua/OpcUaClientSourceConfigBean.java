/*
 * Copyright 2017 StreamSets Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.stage.origin.opcua;

import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.ConfigDefBean;
import com.streamsets.pipeline.api.ListBeanModel;
import com.streamsets.pipeline.api.ValueChooserModel;
import com.streamsets.pipeline.lib.tls.TlsConfigBean;

import java.util.List;

public class OpcUaClientSourceConfigBean {
  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      label = "Resource URL",
      defaultValue = "opc.tcp://localhost:12686/example",
      description = "Specify the OPC UA resource URL",
      displayPosition = 10,
      group = "OPC_UA"
  )
  public String resourceUrl = "";

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      label = "Application Name",
      defaultValue = "SDC OPC UA Client",
      description = "Specify the OPC UA client application name",
      displayPosition = 20,
      group = "OPC_UA"
  )
  public String applicationName = "SDC OPC UA Client";


  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      label = "Application URI",
      defaultValue = "urn:sdc:pipeline:${pipeline:id()}",
      description = "Specify the OPC UA resource URL",
      displayPosition = 30,
      group = "OPC_UA"
  )
  public String applicationUri = "urn:sdc:pipeline:${pipeline:id()}";

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.NUMBER,
      label = "Request Timeout",
      defaultValue = "5000",
      description = "OPC UA request timeout in milliseconds.",
      displayPosition = 40,
      group = "OPC_UA"
  )
  public int requestTimeoutMillis = 0;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      label = "Mode",
      defaultValue = "POLLING",
      displayPosition = 50,
      group = "OPC_UA"
  )
  @ValueChooserModel(OpcUaReadModeChooserValues.class)
  public OpcUaReadMode readMode = OpcUaReadMode.POLLING;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.NUMBER,
      label = "Polling Interval (ms)",
      defaultValue = "5000",
      displayPosition = 60,
      group = "OPC_UA",
      dependsOn = "readMode",
      triggeredByValue = "POLLING"
  )
  public long pollingInterval = 5000;


  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      label = "Security Policy",
      defaultValue = "NONE",
      displayPosition = 70,
      group = "SECURITY"
  )
  @ValueChooserModel(SecurityPolicyChooserValues.class)
  public SecurityPolicyValues securityPolicy = SecurityPolicyValues.NONE;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      label = "Client Private Key Alias",
      defaultValue = "client-ai",
      description = "An alias is specified when you add an entity to the keystore using the -genseckey command to " +
          "generate a secret key, -genkeypair command to generate a key pair (public and private key).",
      displayPosition = 80,
      group = "SECURITY"
  )
  public String clientKeyAlias = "client-ai";

  @ConfigDefBean(groups = "SECURITY")
  public TlsConfigBean tlsConfig = new TlsConfigBean();

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.MODEL,
      label = "NodeId Fetch Mode",
      defaultValue = "MANUAL",
      displayPosition = 200,
      group = "NODE_IDS"
  )
  @ValueChooserModel(NodeIdFetchModeChooserValues.class)
  public NodeIdFetchMode nodeIdFetchMode = NodeIdFetchMode.MANUAL;

  @ConfigDef(
      label = "",
      required = true,
      type = ConfigDef.Type.MODEL,
      defaultValue="",
      description="Fields to generate of the indicated Node Id",
      displayPosition = 210,
      group = "NODE_IDS",
      dependsOn = "nodeIdFetchMode",
      triggeredByValue = "MANUAL"
  )
  @ListBeanModel
  public List<NodeIdConfig> nodeIdConfigs;


  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      label = "Node Id Configs File Path",
      defaultValue = "${runtime:loadResource('nodeIdConfigs.json', false)}",
      description = "Specify the OPC UA resource URL",
      displayPosition = 220,
      group = "NODE_IDS",
      dependsOn = "nodeIdFetchMode",
      triggeredByValue = "FILE"
  )
  public String nodeIdConfigsFilePath;



}
