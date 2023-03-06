export interface Control {
  id: string,
  name: string,
  isOn: boolean,
  type: 'DEVICE' | 'DEVICE_SET'
  lightLevel: number,
  colorTemperature: number,
  lastSeen: Date,
  isReachable: Boolean
}

export interface Room {
  id: string,
  name: string,
  controls: Control[]
}
