/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  View,
  DeviceEventEmitter,
  Text,
} from 'react-native';

function App(): React.JSX.Element {
  DeviceEventEmitter.addListener('InitialEvent', () => {
    console.log('InitialEvent');
  });

  return (
    <SafeAreaView style={{flex: 1}}>
      <View style={styles.container}>
        <Text>Test App</Text>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default App;
