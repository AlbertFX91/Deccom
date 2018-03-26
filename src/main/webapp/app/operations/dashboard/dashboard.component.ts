import { Component, OnInit, OnDestroy } from '@angular/core';
import { CVService } from '../cv/cv.service';
import { ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { Chart } from 'chart.js';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html'
}) export class DashboardComponent implements OnInit, OnDestroy {

    chartData: any[];
    chartDataAux: any[];
    page: any;
    itemsPerPage: number;
    chartOptions: any;
    chartLabels: string[];
    chart: any[];

    constructor(
        public cvService: CVService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.chartData = [];
        this.chartDataAux = [];
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.chartOptions = {
            responsive: true,
            legend: {
                display: true
            },
            scales: {
                xAxes: [{
                    display: true
                }],
                yAxes: [{
                    display: true
                }],
            }
        }
        this.chartLabels = ['January', 'February', 'Mars', 'April'];
    }

    ngOnInit() {
        const pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        }
        this.cvService.findAll(pageSettings).subscribe(
            (data: any) => this.onSuccess(data.json(), data.headers),
            (error: Response) => this.onError(error)
        )
        this.chartDataAux = [
            { data: [330, 600, 260, 700], label: 'Account A' },
            { data: [120, 455, 100, 340], label: 'Account B' },
            { data: [45, 67, 800, 500], label: 'Account C' }
        ]
        this.chart = new Chart('canvas', {
            type: 'line',
            data: {
                labels: this.chartLabels,
                datasets: this.chartData
            },
            options: this.chartOptions
        });
    }

    onSuccess(data: any, headers: any) {
        for (let i = 0; i < data.content.length; i++) {
            if (data.content[i]['status'] === 'RUNNING' && data.content[i]['controlVarEntries'].length > 0) {
                const controlVarEntriesAux: any[] = data.content[i]['controlVarEntries'].slice(Math.max(data.content[i]['controlVarEntries'].length - 5, 0));
                const dataAux: number[] = [];
                for (let j = 0; j < 5; j++) {
                    dataAux.push(controlVarEntriesAux[j]['value']);
                }
                const dato: any = {
                    data: dataAux,
                    label: data.content[i]['name'],
                    backgroundColor: data.content[i]['extractor']['style']['backgroundColor']
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
