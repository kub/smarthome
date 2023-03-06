export interface Configuration {
  scheduledConfigs: ScheduledConfig[]
}

export interface ScheduledConfig {
  scheduleTime: number[];
  roomConfigs: RoomConfig[]
}

export interface RoomConfig {
  name: string;
  deviceSets: DeviceSetConfig[];
  devices: DeviceConfig[];
}

export interface DeviceSetConfig {
  name: string;
  lightLevel: number;
  colorTemperature: number;
}

export interface DeviceConfig {
  name: string;
  lightLevel: number;
  colorTemperature: number;
}

