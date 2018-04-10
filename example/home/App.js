/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Image,
  Button,
  TextInput,
  TouchableOpacity
} from 'react-native';

import NavigationModule from '../../src/bridge/navigation';
import CameraModule from '../../src/bridge/camera';

export default class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      url: 'https://m.baidu.com'
    };
  }

  setURL(url) {
    this.setState({
      url
    })
  }

  openURL() {
    NavigationModule.push(this.state.url);
  }

  openScaner() {
    CameraModule
      .QRCodeScanner()
      .then(str => {
        this.setURL(str);
      })
      .catch(e => {
        console.log(e);
      });
  }

  onUrlTextChange(text) {
    this.setState({
      url: text
    })
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.title}>容器测试主页</Text>
        <View style={styles.editBox}>
          <Text style={styles.label}>URL</Text>
          <View style={styles.line}>
            <TextInput
              style={styles.input}
              placeholder={'wantna a url to open it!'}
              defaultValue={this.state.url}
              onChangeText={this.onUrlTextChange.bind(this)} />
            <TouchableOpacity
              onPress={this.openScaner.bind(this)}>
              <Image style={styles.icon} source={require("./assets/scan.png")} />
            </TouchableOpacity>
          </View>
        </View>
        <Button
          title="打开URL"
          onPress={this.openURL.bind(this)} />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff'
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 40,
  },
  label: {
    fontSize: 16,
    color: '#666'
  },
  line: {
    flexDirection: 'row',
    alignItems: 'center'
  },
  editBox: {
    marginBottom: 40
  },
  input: {
    flex: 1,
  },
  icon: {
    width: 26,
    height: 26
  }
});
