import { Component, OnInit, OnDestroy } from '@angular/core';
import { CVService } from '../cv/cv.service';
import { ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { Chart } from 'chart.js';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html'
}) export class DashboardComponent implements OnInit, OnDestroy {

    chart: any[];
    chartData: any[];
    chartDataAux: any[];
    chartLabels: string[];
    chartOptions: any;
    page: any;
    itemsPerPage: number;

    constructor(
        public cvService: CVService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.chart = [];
        this.chartData = [];
        this.chartDataAux = [];
        this.chartLabels = [];
        this.chartOptions = {};
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
    }

    ngOnInit() {
        const pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.cvService.findAll(pageSettings).subscribe(
            (data: any) => this.onSuccess(data.json(), data.headers),
            (error: Response) => this.onError(error)
        );
        /*
        this.chartDataAux = [
            { data: [330, 600, 260, 700, 800], label: 'Account A', fill: false },
            { data: [120, 455, 100, 340], label: 'Account B', fill: false },
            { data: [45, 67, 800, 500], label: 'Account C', fill: false }
        ];
        */
        this.chartDataAux = [
            {
                data: [{ x: new Date(2018, 1, 1, 12, 50, 55), y: 1 }, { x: new Date(2018, 1, 15, 12, 50, 55), y: 8 }, { x: new Date(2018, 2, 2, 12, 50, 55), y: 3 }],
                label: 'Account A', fill: false
            }
            /*
            { data: [120, 455, 100, 340], label: 'Account B', fill: false },
            { data: [45, 67, 800, 500], label: 'Account C', fill: false }
            */
        ];
        this.chartLabels = ['January', 'February', 'Mars', 'April', 'May'];
        this.chartOptions = {
            responsive: true,
            legend: {
                display: true
            },
            scales: {
                xAxes: [{
                    display: true,
                    type: 'time',
                    time: {
                        displayFormats: {
                            second: 'h:mm:ss a'
                        },
                        unit: 'second'
                    }
                }],
                yAxes: [{
                    display: true
                }],
            }
        };
        this.chart = new Chart('canvas', {
            type: 'line',
            data: {
                labels: this.chartLabels,
                datasets: this.chartData
                // datasets: this.chartDataAux
            },
            options: this.chartOptions
        });
    }

    onSuccess(data: any, headers: any) {
        for (let i = 0; i < data.content.length; i++) {
            if (data.content[i]['status'] === 'RUNNING' && data.content[i]['controlVarEntries'].length > 0) {
                const controlVarEntriesAux: any[] = data.content[i]['controlVarEntries'].slice(Math.max(data.content[i]['controlVarEntries'].length - 5, 0));
                const dataToInsert: any[] = [];
                for (let j = 0; j < 5; j++) {
                    const date: any = new Date(controlVarEntriesAux[j]['creationMoment']);
                    dataToInsert.push({
                        x: new Date(date.getFullYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds()),
                        y: controlVarEntriesAux[j]['value']
                    });
                }
                const dato: any = {
                    data: dataToInsert,
                    label: data.content[i]['name'],
                    backgroundColor: data.content[i]['extractor']['style']['backgroundColor'],
                    fill: false
                }
                this.chartData.push(dato);
            }
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() { }

}
